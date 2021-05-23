package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName

data class AuthParameters(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)