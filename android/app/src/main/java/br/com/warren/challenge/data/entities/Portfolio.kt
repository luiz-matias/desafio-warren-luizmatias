package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Portfolio(
    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("background") val background: Background,
    @SerializedName("totalBalance") val totalBalance: BigDecimal,
    @SerializedName("goalAmount") val goalAmount: BigDecimal?,
    @SerializedName("goalDate") val goalDate: String
)