package se.infomaker.actapublica.downloader

interface DocumentDownloadListener {
    fun onUpdate(completed: Int, total: Int)
}
