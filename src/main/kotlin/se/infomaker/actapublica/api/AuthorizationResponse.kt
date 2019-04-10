package se.infomaker.actapublica.api

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(@SerializedName("access_token") val accessToken: String, @SerializedName("expires_in") val expiresIn: Long, @SerializedName("token_type") val type: String)