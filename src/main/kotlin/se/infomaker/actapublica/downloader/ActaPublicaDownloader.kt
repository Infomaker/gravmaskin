package se.infomaker.actapublica.downloader

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import org.slf4j.LoggerFactory
import retrofit2.Response
import se.infomaker.actapublica.api.*
import se.infomaker.actapublica.app.*
import se.infomaker.actapublica.downloader.group.GroupDestinationProvider
import se.infomaker.actapublica.downloader.group.NoGroupingDestinationProvider
import se.infomaker.actapublica.downloader.group.YearGroupingDestinationProvider
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class ActaPublicaDownloader(val api: ActaPublicaApi): Downloader {
    companion object {
        private val logger = LoggerFactory.getLogger(javaClass)
        val scheduler = Schedulers.from(Executors.newFixedThreadPool(32))
        val downloadScheduler = Schedulers.from(Executors.newFixedThreadPool(32))
        val slowScheduler = Schedulers.from(Executors.newFixedThreadPool(1))
    }

    override fun search(searchType: SearchType, data: List<String>, groupBy: GroupBy, destination: File, listener: DownloadProgressListener, useSlowMode: Boolean) {
        val searchResults =  AtomicInteger(0)

        listener.onProgressUpdate(0, data.size, 0, 0)

        val documentDownloader = DocumentDownloader(object : DocumentDownloadListener {
            override fun onUpdate(completed: Int, total: Int) {
                listener.onProgressUpdate(searchResults.get(), data.size, completed, total)
                notifyIfDone(listener, searchResults.get(), data.size, completed, total)
            }
        })

        val fileProvider = createFileProvider(destination, groupBy)
        var search = scheduler
        var download = downloadScheduler
        if (useSlowMode) {
            search = slowScheduler
            download = slowScheduler
        }
        Observable.defer {
            if (useSlowMode) {
                val intervalRange = Observable.intervalRange(0, data.size.toLong(), 0, 1000, TimeUnit.MILLISECONDS)
                val values = Observable.fromIterable(data)
                return@defer Observable.zip(intervalRange, values, BiFunction<Long, String, String> { _, searchTerm -> searchTerm})
            }
            else {
                return@defer Observable.fromIterable(data)
            }
        }.observeOn(search).subscribe { searchTerm ->
            SearchFetcher(searchTerm, Single.just(searchTerm).observeOn(search).flatMap { createSearch(searchType, it) }, fileProvider).searchAndQueDownloads(download, object : SearchListener {
                override fun onSearch(results: Int) {
                    listener.onProgressUpdate(searchResults.incrementAndGet(), data.size, documentDownloader.completed(), documentDownloader.total())
                }

                override fun onError(error: Throwable) {
                    logger.error("Oh no!", error)
                }

            }, documentDownloader)
        }
    }

    private fun createSearch(searchType: SearchType, searchTerm: String) : Single<Response<SearchResponse>>{
        return when(searchType) {
            SearchType.PERSONNUMMER -> {
                api.search(PersonnummerSearch.create(searchTerm))
            }
            SearchType.FREE_TEXT -> {
                api.search(FreeTextSearch(searchTerm))
            }
            SearchType.ORGANIZATION -> {
                api.search(OrganizationSearch(listOf(searchTerm)))
            }
        }
    }

    private fun notifyIfDone(listener: DownloadProgressListener, completeSearch: Int, totalSearch: Int, completedDownload: Int, totalDownload: Int) {
        if (completeSearch == totalSearch && completedDownload == totalDownload) {
            listener.onDone()
        }
    }

    private fun createFileProvider(destination: File, groupBy: GroupBy): GroupDestinationProvider {
        return when(groupBy) {
            GroupBy.NONE -> {
                NoGroupingDestinationProvider(destination)
            }
            GroupBy.YEAR -> {
                YearGroupingDestinationProvider(destination)
            }
        }
    }
}