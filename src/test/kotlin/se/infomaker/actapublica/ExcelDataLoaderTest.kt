package se.infomaker.actapublica

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import se.infomaker.actapublica.app.ExcelDataLoader
import java.io.File

class ExcelDataLoaderTest {

/*
    @Test
    fun testGetSheetNames() {
        val excelDataLoader = ExcelDataLoader(File("./resources/test_data.xlsx"))

        val sheets = excelDataLoader.getSheetNames()
        Assertions.assertTrue(sheets[0] == "Sheet1")
    }

    @Test
    fun testGetValidColumns() {
        val excelDataLoader = ExcelDataLoader(File("./resources/test_data.xlsx"))
        val sheets = excelDataLoader.getSheetNames()
        val validColumns = excelDataLoader.getColumns(sheets[0])
        Assertions.assertTrue(validColumns.size == 2)
    }

    @Test
    fun testExtractColumnData() {
        val excelDataLoader = ExcelDataLoader(File("./resources/test_data.xlsx"))
        val sheets = excelDataLoader.getSheetNames()

        val personnummerData = excelDataLoader.getColumnData(sheets[0], "D5")
        Assertions.assertTrue(personnummerData.size == 9)
        Assertions.assertNotNull(personnummerData)

        val organisationnummerData = excelDataLoader.getColumnData(sheets[0], "E6")
        Assertions.assertTrue(organisationnummerData.size == 3)
        Assertions.assertNotNull(organisationnummerData)
    }
*/
}