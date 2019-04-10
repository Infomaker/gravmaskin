package se.infomaker.actapublica.downloader

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.schedulers.Schedulers
import se.infomaker.actapublica.downloader.file.FileDownloaderProvider
import se.infomaker.actapublica.downloader.file.FileDownloader
import se.infomaker.actapublica.downloader.file.DownloadDocumentJob
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class DocumentDownloader(val listener: DocumentDownloadListener) {
    companion object {
        val scheduler = Schedulers.from(Executors.newFixedThreadPool(4))
    }
    private val downloader: FileDownloader = FileDownloaderProvider.createService()

    val jobs = mutableListOf<DownloadDocumentJob>()
    var completedCount = AtomicInteger(0)


    fun add(url: String, destination: File) {
        val job = DownloadDocumentJob(url, destination)
        jobs.add(job)
        listener.onUpdate(completedCount.get(), jobs.size)
        Single.just(url).observeOn(scheduler).flatMap { downloader.downloadFile(it) }.subscribe { result, error ->
            result?.body()?.let {
                val parentFile = destination.parentFile
                if (!parentFile.exists()) {
                    parentFile.mkdirs()
                }
                destination.createNewFile()
                destination.writeBytes(it.bytes())
            }
            listener.onUpdate(completedCount.incrementAndGet(), jobs.size)

            // TODO care for error handling
        }
    }

    fun total() : Int {
        return jobs.size
    }

    fun completed() : Int {
        return completedCount.get()
    }
}