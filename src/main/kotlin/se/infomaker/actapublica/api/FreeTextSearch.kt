package se.infomaker.actapublica.api

data class FreeTextSearch(val query: String, val size:Int = 1000, val page: Int = 0)
