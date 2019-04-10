package se.infomaker.actapublica.downloader

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import se.infomaker.actapublica.api.SearchResponse
import se.infomaker.actapublica.downloader.group.GroupDestinationProvider
import java.util.concurrent.Executors

class SearchFetcher(private val searchTerm : String, private val search: Single<Response<SearchResponse>>, private val fileProvider: GroupDestinationProvider) {

    fun searchAndQueDownloads(scheduler: Scheduler, listener: SearchListener, downloader: DocumentDownloader) {

        search.observeOn(scheduler).subscribe { response, error ->
            if (error != null) {
                listener.onError(error)
            }
            else {
                val body = response.body()
                val results = body?.hits

                results?.forEach {
                    it.document_download_link?.let { url ->
                        downloader.add(url, fileProvider.getDestination(searchTerm, it))
                    }
                }
                listener.onSearch(results?.size ?: 0)
            }
        }
    }
}

interface SearchListener {
    fun onSearch(results: Int)
    fun onError(error: Throwable)
}
