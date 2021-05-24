package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName

data class Background(
    @SerializedName("thumb") val thumb: String,
    @SerializedName("small") val small: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("raw") val raw: String
)