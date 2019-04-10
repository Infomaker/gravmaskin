package se.infomaker.actapublica.downloader.group

import se.infomaker.actapublica.api.Hit
import java.io.File
import java.io.IOException
import java.util.UUID


class NoGroupingDestinationProvider(private val destination: File) : GroupDestinationProvider {

    override fun getDestination(search: String, hit: Hit): File {
        if (!destination.exists()) {
            destination.mkdirs()
        }
        if (!destination.isDirectory) {
            throw IOException("Cant create files in file")
        }
        val searchDir = File(destination, search)
        if (!searchDir.exists())  {
            searchDir.mkdir()
        }

        return File(searchDir, createFileName(hit))
    }
}