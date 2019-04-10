package se.infomaker.actapublica.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class ActaPublicaAccessTokenInterceptor(val clientId: String, val clientSecret: String) : Interceptor {

    companion object {
        private val logger = LoggerFactory.getLogger(javaClass)
    }

    val accessTokenClient: ActaPublicaAccessTokenApi
    var accessToken: String? = null
    var tokenValidTo: Date? = null

    init {
        val builder = OkHttpClient().newBuilder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        builder.addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://api.arkivet.actapublica.se/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        accessTokenClient = retrofit.create(ActaPublicaAccessTokenApi::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (chain.request().header("Authorization") == null) {
            val builder = chain.request().newBuilder()
            if (tokenValidTo?.time ?: 0 < System.currentTimeMillis()) {
                updateToken()
            }
            logger.debug("Adding token")
            builder.addHeader("Authorization", "Bearer $accessToken")
            return chain.proceed(builder.build())
        }
        else {
            return chain.proceed(chain.request())
        }
    }

    private fun updateToken() {
        logger.debug("Updating token")
        val response = accessTokenClient.authorize(Authorization(client_id = clientId, client_secret = clientSecret)).blockingGet()
        if (response.isSuccessful) {
            response.body()?.let {
                accessToken = it.accessToken
                tokenValidTo = Date(System.currentTimeMillis() + (it.expiresIn * 1000))
                logger.debug("New token valid to $tokenValidTo")
            }
        }
    }
}