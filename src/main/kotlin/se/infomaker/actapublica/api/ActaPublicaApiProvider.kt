package se.infomaker.actapublica.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ActaPublicaApiProvider {
    fun create(clientId: String, clientSecret: String): ActaPublicaApi {
        val builder = OkHttpClient().newBuilder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging).build()
        builder.addInterceptor(ActaPublicaAccessTokenInterceptor(clientId, clientSecret))

        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://api.arkivet.actapublica.se/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ActaPublicaApi::class.java)
    }

}