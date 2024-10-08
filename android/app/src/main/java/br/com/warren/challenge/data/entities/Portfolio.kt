package br.com.warren.challenge.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Portfolio(
    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("background") val background: Background,
    @SerializedName("totalBalance") val totalBalance: BigDecimal,
    @SerializedName("goalAmount") val goalAmount: BigDecimal?,
    @SerializedName("goalDate") val goalDate: String
) : Parcelable