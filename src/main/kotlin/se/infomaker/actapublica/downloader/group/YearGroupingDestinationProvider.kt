package se.infomaker.actapublica.downloader.group

import se.infomaker.actapublica.api.Hit
import java.io.File
import java.io.IOException
import java.util.*


class YearGroupingDestinationProvider(private val destination: File) : GroupDestinationProvider {

    override fun getDestination(searchTerm: String, hit: Hit): File {
        if (!destination.exists()) {
            destination.mkdirs()
        }
        if (!destination.isDirectory) {
            throw IOException("Cant create files in file")
        }
        val searchDir = File(destination, searchTerm)
        if (!searchDir.exists())  {
            searchDir.mkdir()
        }
        val cal = Calendar.getInstance()
        cal.time = hit.datum
        val year = "" + cal.get(Calendar.YEAR)

        val yearDir = File(searchDir, year)
        if (!yearDir.exists())  {
            yearDir.mkdir()
        }

        return File(yearDir, createFileName(hit))
    }
}