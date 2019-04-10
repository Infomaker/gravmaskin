package se.infomaker.actapublica.downloader.file

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FileDownloader {

    @GET
    fun downloadFile(@Url fileUrl: String): Single<Response<ResponseBody>>
}