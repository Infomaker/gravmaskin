package se.infomaker.actapublica.app

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.util.CellAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

class ExcelDataLoader(var file: File): DataLoader {

    override fun getSheetNames(): List<String> {
        val data = mutableListOf<String>()
        val excelFile = FileInputStream(file)
        val workbook = XSSFWorkbook(excelFile)
        val sheetCount = workbook.numberOfSheets

        var i = 0
        while (i < sheetCount) {
            println(workbook.getSheetAt(i).sheetName)
            data.add(workbook.getSheetAt(i).sheetName)
            i++
        }

        workbook.close()
        excelFile.close()
        return data
    }

    override fun getColumns(sheetName: String): Map<String, String> {
        val data = mutableMapOf<String,String>()
        val columnData = mutableMapOf<String, CellAddress>()
        val excelFile = FileInputStream(file)
        val workbook = XSSFWorkbook(excelFile)

        val sheet = workbook.getSheet(sheetName)
        val rows = sheet.iterator()

        while (rows.hasNext()) {
            val currentRow = rows.next()
            val cellsInRow = currentRow.iterator()
            while (cellsInRow.hasNext()) {
                val currentCell = cellsInRow.next()

                when (currentCell.cellTypeEnum) {
                    CellType.STRING -> {
                        var skip = true
                        columnData.forEach {entry ->
                            if(entry.value.column == currentCell.address.column) {
                                skip = false
                            }
                        }
                        if (skip) {
                            columnData[currentCell.stringCellValue] = currentCell.address
                        }
                    }
                    else -> { }
                }
            }
        }
        workbook.close()
        excelFile.close()
        columnData.forEach {
            data[it.key] = it.value.formatAsString()
        }
        return data
    }

    override fun getColumnData(sheetName:String, startCell: String): List<String> {

        val data = mutableListOf<String>()
        val excelFile = FileInputStream(file)
        val workbook = XSSFWorkbook(excelFile)

        val sheet = workbook.getSheet(sheetName)
        val rows = sheet.iterator()
        val column = CellAddress(startCell)

        while (rows.hasNext()) {
            val currentRow = rows.next()
            val cellsInRow = currentRow.iterator()
            while (cellsInRow.hasNext()) {
                val currentCell = cellsInRow.next()
                if (currentCell.address.column == column.column && currentCell.address.row > column.row) {
                    when (currentCell.cellTypeEnum) {
                        CellType.STRING -> {
                            data.add(currentCell.stringCellValue)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
        workbook.close()
        excelFile.close()
        return data
    }
}