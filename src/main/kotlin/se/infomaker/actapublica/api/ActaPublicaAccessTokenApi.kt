package se.infomaker.actapublica.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ActaPublicaAccessTokenApi {
    @POST("authorize")
    fun authorize(@Body authorization: Authorization): Single<Response<AuthorizationResponse>>
}
