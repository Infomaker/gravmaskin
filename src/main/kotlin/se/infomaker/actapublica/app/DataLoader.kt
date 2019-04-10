package se.infomaker.actapublica.app

interface DataLoader {

    fun getSheetNames(): List<String>

    fun getColumns(sheetName: String): Map<String, String>

    fun getColumnData(sheetName: String, startCell: String): List<String>
}