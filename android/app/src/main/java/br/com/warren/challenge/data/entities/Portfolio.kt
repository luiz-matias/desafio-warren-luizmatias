package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName

data class Portfolio(
    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("background") val background: Background,
    @SerializedName("totalBalance") val totalBalance: Double,
    @SerializedName("goalAmount") val goalAmount: Int,
    @SerializedName("goalDate") val goalDate: String
)