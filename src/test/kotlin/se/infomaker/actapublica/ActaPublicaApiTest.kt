package se.infomaker.actapublica

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import se.infomaker.actapublica.api.ActaPublicaAccessTokenInterceptor
import se.infomaker.actapublica.api.ActaPublicaApiProvider
import se.infomaker.actapublica.api.Authorization
import se.infomaker.actapublica.api.PersonnummerSearch

class ActaPublicaApiTest {

/*
    @Test
    fun testGetApi() {
        val api = ActaPublicaApiProvider.create("4565693567028166656", "6a1f6a27-4fc3-11e9-b7a1-022d8d36c872")
        Assertions.assertNotNull(api)
    }

    @Test
    fun testGetAccessToken() {
        val tokenInterceptor = ActaPublicaAccessTokenInterceptor(clientId = "4565693567028166656", clientSecret = "6a1f6a27-4fc3-11e9-b7a1-022d8d36c872")
        val response = tokenInterceptor.accessTokenClient.authorize(Authorization(client_id = "4565693567028166656", client_secret = "6a1f6a27-4fc3-11e9-b7a1-022d8d36c872")).blockingGet()
        Assertions.assertNotNull(response.body())
    }

    @Test
    fun testSearch() {
        val api = ActaPublicaApiProvider.create("4565693567028166656", "6a1f6a27-4fc3-11e9-b7a1-022d8d36c872")
        val personnummerSearch = PersonnummerSearch.create("010904-4271")
        val response = api.search(personnummerSearch).blockingGet()
        Assertions.assertEquals(2, response.body()?.total)
    }
*/
}