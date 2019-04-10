package se.infomaker.actapublica.downloader

interface DownloadProgressListener {
    fun onProgressUpdate(completeSearches: Int, totalSearches: Int, completeDownloads: Int, totalDownloads: Int)
    fun onDone()
}
