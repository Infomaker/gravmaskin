package se.infomaker.actapublica.api

import com.google.gson.annotations.SerializedName

data class PersonnummerSearch(@SerializedName("personnummer") val personnummerCondition: PersonnummerCondition, val size: Int = 1000) {
    companion object {
        fun create(personnummer: String, page: Int = 0): PersonnummerSearch {
            return PersonnummerSearch(personnummerCondition = PersonnummerCondition("AND", listOf(personnummer)), size = 1000)
        }
    }
}

data class PersonnummerCondition(val condition: String, val values: List<String>)
