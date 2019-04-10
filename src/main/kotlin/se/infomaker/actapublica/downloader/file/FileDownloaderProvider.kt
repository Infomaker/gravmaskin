package se.infomaker.actapublica.downloader.file

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object FileDownloaderProvider {

    fun createService(): FileDownloader {
        val builder = OkHttpClient().newBuilder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        builder.addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://anythinggoes/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(FileDownloader::class.java)
    }
}