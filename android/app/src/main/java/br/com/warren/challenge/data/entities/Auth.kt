package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)