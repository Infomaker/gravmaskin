package se.infomaker.actapublica.downloader

import se.infomaker.actapublica.app.GroupBy
import se.infomaker.actapublica.app.SearchType
import java.io.File

interface Downloader {
    fun search(searchType: SearchType, data: List<String>, groupBy: GroupBy, destination: File, listener: DownloadProgressListener, useSlowMode: Boolean)
}