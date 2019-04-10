package se.infomaker.actapublica.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ActaPublicaApi {
    @POST("search")
    fun search(@Body search: PersonnummerSearch) : Single<Response<SearchResponse>>

    @POST("search")
    fun search(@Body search: FreeTextSearch) : Single<Response<SearchResponse>>


    @POST("search")
    fun search(@Body search: OrganizationSearch) : Single<Response<SearchResponse>>
}
