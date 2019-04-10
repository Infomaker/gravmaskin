package se.infomaker.actapublica.view

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.text.FontWeight
import se.infomaker.actapublica.downloader.DownloadProgressListener
import tornadofx.*

class ProgressView : View ("Progress")
{
    private val FONTWEIGHT = FontWeight.NORMAL
    private val FONTSIZE = 14.px

    private var status = SimpleStringProperty("Searching...")
    private lateinit var statusLabel: Label

    private lateinit var closeButton: Button

    private var done = SimpleBooleanProperty()
    private val progressListener = DLProgressListener(status, done)



    override val root = vbox {
        paddingTop = 10
        paddingLeft = 10
        label("Searches                                                            ") {
            style {
                fontWeight = FONTWEIGHT
                fontSize = FONTSIZE
            }
        }
        useMaxWidth = true
        progressbar(progressListener.searches) {
            paddingLeft = 10
            paddingRight = 10
            useMaxWidth = true
        }
        label("Files") {
            style {
                fontWeight = FONTWEIGHT
                fontSize = FONTSIZE
            }
        }
        useMaxWidth = true
        progressbar(progressListener.files) {
            paddingLeft = 10
            paddingRight = 10
            useMaxWidth = true
        }
        useMaxWidth = true
        useMaxHeight = true
        statusLabel = label(status) {
            useMaxWidth = true
            useMaxHeight = true
            style {
                fontWeight = FONTWEIGHT
                fontSize = FONTSIZE
            }
        }
        paddingLeft = 10
        paddingRight = 10
        paddingBottom = 10
        closeButton = button("Close") {
            useMaxWidth = true
            useMaxHeight = true
            action {
                close()
            }
        }
        closeButton.defaultButtonProperty().value=true
        closeButton.enableWhen( done )

    }

    fun updateStatus(newStatus: String)
    {
        status.value = newStatus
    }

    fun getProgressListener(): DownloadProgressListener
    {
        return progressListener
    }

    class DLProgressListener(statusProperty: SimpleStringProperty, doneProperty: SimpleBooleanProperty) : DownloadProgressListener
    {
        var searches = SimpleDoubleProperty()
        var files = SimpleDoubleProperty()
        var statusUpdate = statusProperty
        var done = doneProperty


        override fun onProgressUpdate(completeSearches: Int, totalSearches: Int, completeDownloads: Int, totalDownloads: Int) {
            println("onProgressUpdate: completeSearches=${completeSearches} totalSearches=${totalSearches} completeDownloads=${completeDownloads} totalDownloads=${totalDownloads}")
            Platform.runLater {
                if (totalSearches != 0) {
                    searches.value = (completeSearches.toDouble() / totalSearches.toDouble())
                    println("progress searches: ${searches.value}")
                }
                if (totalDownloads != 0) {
                    files.value = (completeDownloads.toDouble() / totalDownloads.toDouble())
                    println("progress files: ${files.value}")
                }
                statusUpdate.value = "Searches: ${completeSearches} (${totalSearches}), Files: ${completeDownloads} (${totalDownloads})"
            }
        }

        override fun onDone() {
            Platform.runLater {
                done.value = true
                searches.value = 1.0
                files.value = 1.0
                statusUpdate.value = statusUpdate.value + "\nDone!"
                println("onDone: Done!")
            }
        }
    }
}