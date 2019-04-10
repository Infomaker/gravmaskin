package se.infomaker.actapublica.app

import se.infomaker.actapublica.api.ActaPublicaApiProvider
import se.infomaker.actapublica.downloader.ActaPublicaDownloader
import se.infomaker.actapublica.downloader.Downloader

object DownloaderProvider {
    fun getDownloader(clientId: String, clientSecret: String): Downloader {
        return ActaPublicaDownloader(ActaPublicaApiProvider.create(clientId, clientSecret))
    }
}