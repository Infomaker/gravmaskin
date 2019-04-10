package se.infomaker.actapublica.api

import java.util.Date

data class SearchResponse (val total: Int, val hits: List<Hit>)

data class Hit(val id: String?, val created_at: String, val datum: Date, val filename: String?, val filesize: Long?, val document_download_link: String?)
