package se.infomaker.actapublica.downloader.group

import se.infomaker.actapublica.api.Hit
import java.io.File

interface GroupDestinationProvider {
    fun getDestination(searchTerm: String, hit: Hit): File

    fun createFileName(hit:Hit) : String {
        val fileExtension = hit.filename?.substringAfterLast(".")
        val fileName = hit.filename?.substringBeforeLast(".")
        return "$fileName-${hit.id}.$fileExtension"
    }
}
