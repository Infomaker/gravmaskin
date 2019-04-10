package se.infomaker.actapublica

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import se.infomaker.actapublica.api.ActaPublicaApiProvider
import se.infomaker.actapublica.downloader.ActaPublicaDownloader
import se.infomaker.actapublica.downloader.DownloadProgressListener
import se.infomaker.actapublica.app.GroupBy
import se.infomaker.actapublica.app.SearchType
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class ActaPublicaDownloaderTest {
    companion object {
        val clientId = "4565693567028166656"
        val clientSecret = "6a1f6a27-4fc3-11e9-b7a1-022d8d36c872"
    }
/*
    @Test
    fun searchPersonnummer(@TempDir tempDir: File) {
        val downloader = ActaPublicaDownloader(ActaPublicaApiProvider.create(clientId, clientSecret))

        val latch = CountDownLatch(1)

        val output = tempDir.resolve("010904-4271")
        var total = 0
        downloader.search(SearchType.PERSONNUMMER, listOf("010904-4271"), GroupBy.NONE, tempDir, object : DownloadProgressListener {
            override fun onProgressUpdate(completeSearches: Int, totalSearches: Int, completeDownloads: Int, totalDownloads: Int) {
                total = totalDownloads
                if (completeSearches == totalSearches && completeDownloads == totalDownloads) {
                    latch.countDown()
                }
            }

            override fun onDone() {
                latch.countDown()
            }
        })
        latch.await(20, TimeUnit.SECONDS)


        assertTrue(output.exists() && output.isDirectory)
        assertEquals(total, output.list().size)
    }

    @Test
    fun downloadPersonnummerGroupByYear(@TempDir tempDir: File) {
        val downloader = ActaPublicaDownloader(ActaPublicaApiProvider.create(clientId, clientSecret))

        val latch = CountDownLatch(1)

        val output = tempDir.resolve("800920-0893")
        var total = 0
        downloader.search(SearchType.PERSONNUMMER, listOf("800920-0893"), GroupBy.YEAR, tempDir, object : DownloadProgressListener {
            override fun onProgressUpdate(completeSearches: Int, totalSearches: Int, completeDownloads: Int, totalDownloads: Int) {
                total = totalDownloads
                if (completeSearches == totalSearches && completeDownloads == totalDownloads) {
                    latch.countDown()
                }
            }

            override fun onDone() {
                latch.countDown()
            }
        })
        latch.await(20, TimeUnit.SECONDS)


        assertTrue(output.exists() && output.isDirectory)
        assertEquals(total, output.list().size)
    }

    @Test
    fun downloadOrganization(@TempDir tempDir: File) {
        val orgnumber = "556571-0489"

        val downloader = ActaPublicaDownloader(ActaPublicaApiProvider.create(clientId, clientSecret))

        val latch = CountDownLatch(1)

        val output = tempDir.resolve(orgnumber)
        var total = 0
        downloader.search(SearchType.ORGANIZATION, listOf(orgnumber), GroupBy.NONE, tempDir, object : DownloadProgressListener {
            override fun onProgressUpdate(completeSearches: Int, totalSearches: Int, completeDownloads: Int, totalDownloads: Int) {
                total = totalDownloads
                if (completeSearches == totalSearches && completeDownloads == totalDownloads) {
                    latch.countDown()
                }
            }

            override fun onDone() {
                latch.countDown()
            }
        })
        latch.await(20, TimeUnit.SECONDS)


        assertTrue(output.exists() && output.isDirectory)
        assertEquals(total, output.list().size)

    }
*/
}