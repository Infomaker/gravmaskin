package se.infomaker.actapublica.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import javafx.scene.text.FontWeight
import javafx.stage.FileChooser
import se.infomaker.actapublica.app.DownloaderProvider
import se.infomaker.actapublica.app.ExcelDataLoader
import se.infomaker.actapublica.app.GroupBy
import se.infomaker.actapublica.app.SearchType
import tornadofx.*
import java.io.File

class MainView : View("Acta Publica Grävmaskin") {

    private val FONTWEIGHT = FontWeight.NORMAL
    private val FONTSIZE = 12.px

    private var inputFileName = SimpleStringProperty()
    private var directoryName = SimpleStringProperty()

    private var sheetCollection = FXCollections.observableArrayList<String>()
    private var columnsCollection = FXCollections.observableArrayList<String>()
    private var columnNames = mutableListOf<String>()
    private lateinit var sheets: List<String>
    private lateinit var columnsMap: Map<String, String>
    private lateinit var inputFiles: List<File>
    private lateinit var dataLoader: ExcelDataLoader
    private lateinit var outputDirectory: File

    private val settingsView = SettingsView()
    private var downloader = DownloaderProvider.getDownloader(settingsView.getClientID(), settingsView.getSecret())

    private lateinit var sheetCombo: ComboBox<String>
    private lateinit var columnCombo: ComboBox<String>
    private lateinit var groupbyCombo: ComboBox<String>
    private lateinit var searchTypeCombo: ComboBox<String>
    private  val useSlowMode = SimpleBooleanProperty(false)

    private lateinit var progressView: ProgressView

    override val root = vbox{

        vbox {
            vgrow = Priority.ALWAYS
            alignment = Pos.CENTER
            paddingTop = 10
            paddingLeft = 10
            // Input file
            hbox {
                paddingBottom = 10.0
                label("File") {
                    paddingRight = 65.0
                    style {
                        fontWeight = FONTWEIGHT
                        fontSize = FONTSIZE
                    }
                }

                paddingLeft = 5
                textfield(inputFileName) {
                    isFillWidth = true
                    useMaxWidth = true
                    useMaxHeight = true
                    paddingRight = 5.0
                    editableProperty().set(false)
                    vboxConstraints {
                        vGrow = Priority.ALWAYS
                    }
                    hboxConstraints {
                        hGrow = Priority.ALWAYS
                    }
                }
                hbox {
                    paddingRight = 8.0
                }
                button("Open  ") {
                    useMaxHeight = true
                    val filter = FileChooser.ExtensionFilter("Excel", listOf("*.xls", "*.xlsx"))
                    action {
                        inputFiles = chooseFile("Select Input File", arrayOf(filter), FileChooserMode.Single)
                        if (inputFiles.isNotEmpty()) {
                            println("File: ${inputFiles[0]}")
                            inputFileName.value = inputFiles[0].absolutePath
                            dataLoader = ExcelDataLoader(inputFiles[0])
                            sheets = dataLoader.getSheetNames()
                            sheetCollection.clear()
                            sheetCollection.addAll(sheets)
                        }
                    }
                    paddingAll = 5.0
                }
                hbox {
                    paddingRight = 5.0
                }
            }

            hbox {
                paddingBottom = 10.0
                // Output dir
                label("Destination") {
                    paddingRight = 20.0
                    style {
                        fontWeight = FONTWEIGHT
                        fontSize = FONTSIZE
                    }
                }
                paddingLeft = 5
                textfield(directoryName) {
                    isFillWidth = true
                    useMaxWidth = true
                    useMaxHeight = true
                    paddingRight = 5.0
                    editableProperty().set(false)
                    vboxConstraints {
                        vGrow = Priority.ALWAYS
                    }
                    hboxConstraints {
                        hGrow = Priority.ALWAYS
                    }
                }
                hbox {
                    paddingRight = 5.0
                }
                button("Select") {
                    useMaxHeight = true
                    action {
                        val dir = chooseDirectory("Select Output Directory", null)
                        if (dir != null) {
                            outputDirectory = dir
                            directoryName.value = outputDirectory.absolutePath
                        } else {
                            directoryName.value = ""
                        }
                    }
                    paddingAll = 5.0
                }
                hbox {
                    paddingRight = 5.0
                }
            }
        }

        vbox{
            hbox {
                // Sheet
                vbox {
                    paddingLeft = 10
                    alignment = Pos.CENTER_LEFT
                    paddingRight = 10
                    // Input file
                    label("Sheet") {
                        style {
                            fontWeight = FONTWEIGHT
                            fontSize = FONTSIZE

                        }
                    }
                    sheetCombo = combobox {
                        items = sheetCollection
                    }
                    sheetCombo.setOnAction {
                        val sheet = sheetCombo.selectedItem
                        if (sheet != null) {
                            columnsMap = dataLoader.getColumns(sheet)
                            columnsMap.forEach { columnNames.add(it.key) }
                            columnNames.sort()
                            columnsCollection.clear()
                            columnsCollection.addAll(columnNames)
                        }
                    }

                }
                // Column
                vbox {
                    alignment = Pos.CENTER_LEFT
                    label("Column") {
                        style {
                            fontWeight = FONTWEIGHT
                            fontSize = FONTSIZE
                            paddingRight = 10
                        }
                    }
                    columnCombo = combobox {
                        items = columnsCollection

                    }
                    paddingRight = 10
                }
                // Search Type
                vbox {
                    alignment = Pos.CENTER_LEFT
                    label("Search Type") {
                        style {
                            fontWeight = FONTWEIGHT
                            fontSize = FONTSIZE

                        }
                    }
                    searchTypeCombo = combobox {
                        items = FXCollections.observableArrayList(SearchType.values().map { e -> e.name })
                    }
                    paddingRight = 10
                }
                // Group by
                vbox {
                    alignment = Pos.CENTER_LEFT
                    label("Group by") {
                        style {
                            fontWeight = FONTWEIGHT
                            fontSize = FONTSIZE

                        }
                    }
                    groupbyCombo = combobox {
                        items = FXCollections.observableArrayList(GroupBy.values().map { e -> e.name })
                    }
                    paddingRight = 10
                }

            }
        }
        vbox {
            paddingLeft = 10
            paddingRight = 10
            paddingTop = 10
            paddingBottom = 10
            button("Download") {
                paddingTop = 10
                paddingBottom = 10
                useMaxWidth = true
                action {
                    val sheet = sheetCombo.selectedItem
                    val columnName = columnCombo.selectedItem
                    val searchTypeName = searchTypeCombo.selectedItem
                    val groupByName = groupbyCombo.selectedItem
                    val column = columnsMap[columnName]

                    progressView = ProgressView()

                    when {
                        sheet == null -> progressView.updateStatus("Invalid Sheet selection")
                        columnName == null -> progressView.updateStatus("Invalid Column selection")
                        column == null -> progressView.updateStatus("Invalid Column selection")
                        searchTypeName == null -> progressView.updateStatus("Invalid Search Type selection")
                        groupByName == null -> progressView.updateStatus("Invalid Group by selection")
                        else -> {
                            println ("Getting column data for sheet $sheet and column $column")
                            val columnData = dataLoader.getColumnData(sheet, column)
                            val searchType = SearchType.valueOf(searchTypeName)
                            val groupBy = GroupBy.valueOf(groupByName)
                            columnData.forEach { println(it) }

                            progressView.openModal()

                            Thread {
                                downloader.search(searchType, columnData, groupBy, outputDirectory, progressView.getProgressListener(), useSlowMode.get())
                            }.start()
                        }
                    }
                }
            }
        }

        hbox {
            alignment = Pos.CENTER_RIGHT
            vbox {
                alignment = Pos.CENTER_RIGHT
                paddingLeft = 10
                paddingRight = 10
                paddingBottom = 10
                checkbox("Slow mode", useSlowMode) {
                    paddingRight = 10
                }
            }
            useMaxWidth = true
            vbox {
                alignment = Pos.CENTER_RIGHT
                paddingLeft = 10
                paddingRight = 10
                paddingBottom = 10


                button {
                    //paddingLeft = 10

                    addClass("icon-only")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.COG)

                    action {
                        settingsView.openModal()
                        downloader = DownloaderProvider.getDownloader(settingsView.getClientID(), settingsView.getSecret())
                    }
                }
            }
        }

    }
}
