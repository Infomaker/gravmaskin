package se.infomaker.actapublica.view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.text.FontWeight
import tornadofx.*
import java.io.File

class SettingsView: View("Settings")
{
    private val CLIENTID = "clientid.conf"
    private val SECRET = "secret.conf"

    private val FONTWEIGHT = FontWeight.NORMAL
    private val FONTSIZE = 12.px

    private lateinit var clientIdField: TextField
    private lateinit var secretField: TextField


    private lateinit var clientIdLabel: Label
    private lateinit var secretLabel: Label

    override val root = vbox {

        hbox {
            alignment = Pos.CENTER
            paddingTop = 10
            hbox {
                paddingLeft = 10
            }
            clientIdLabel = label("Client ID") {
                paddingBottom = 10
                style {
                    fontWeight = FONTWEIGHT
                    fontSize = FONTSIZE
                }
            }
            useMaxWidth = true
            hbox {
                paddingRight = 5
            }
            clientIdField = textfield(loadSettingsFile(CLIENTID)) {
                paddingBottom = 10
                useMaxWidth = true
                isFillWidth = true
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                }
            }
            paddingRight = 10
        }
        hbox {
            alignment = Pos.CENTER
            paddingTop = 10
            hbox {
                paddingLeft = 5
            }
            secretLabel = label("Secret  ") {
                paddingBottom = 10
                style {
                    fontWeight = FONTWEIGHT
                    fontSize = FONTSIZE
                }
            }
            useMaxWidth = true
            paddingLeft = 5
            hbox {
                paddingRight = 10
            }
            secretField = textfield(loadSettingsFile(SECRET)) {
                paddingBottom = 10
                paddingLeft = 5
                paddingRight = 10
                useMaxWidth = true
                isFillWidth = true
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                }
            }
            paddingRight = 10
        }

        hbox {
            paddingRight = 10
            paddingTop = 15
            paddingBottom = 10
            alignment = Pos.BASELINE_RIGHT
            button("Save") {
                action {
                    val clientId = clientIdField.text
                    if (clientId != null) {
                        saveSettingsFile(CLIENTID, clientId)
                    }

                    val secret = secretField.text
                    if (secret != null) {
                        saveSettingsFile(SECRET, secret)
                    }
                    close()
                }
            }
        }
    }

    fun getClientID(): String
    {
        return loadSettingsFile(CLIENTID)
    }

    fun getSecret(): String
    {
        return loadSettingsFile(SECRET)
    }

    private fun saveSettingsFile(name: String, value: String)
    {
        val file = File(AppPaths().getSettingsDir(), name)
        file.writeText(value)
    }

    private fun loadSettingsFile(name: String): String
    {
        val file = File(AppPaths().getSettingsDir(), name)
        if (file.exists()) {
            return file.readText()
        }
        return ""
    }
}