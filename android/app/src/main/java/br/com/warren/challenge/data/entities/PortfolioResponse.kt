package br.com.warren.challenge.data.entities

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(
    @SerializedName("portfolios") val portfolios: List<Portfolio>
)