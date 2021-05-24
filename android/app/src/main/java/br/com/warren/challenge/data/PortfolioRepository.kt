package br.com.warren.challenge.data

import br.com.warren.challenge.data.entities.Portfolio

interface PortfolioRepository {

    suspend fun getPortfolio(): List<Portfolio>

}