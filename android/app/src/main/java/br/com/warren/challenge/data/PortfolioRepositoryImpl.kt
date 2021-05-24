package br.com.warren.challenge.data

import br.com.warren.challenge.app.exceptions.HttpException
import br.com.warren.challenge.app.exceptions.UnauthorizedException
import br.com.warren.challenge.data.entities.Portfolio
import br.com.warren.challenge.data.webservice.WebService

class PortfolioRepositoryImpl(private val webService: WebService) : PortfolioRepository {

    override suspend fun getPortfolio(): List<Portfolio> {
        val portfolioResponse = webService.getPortfolio()
        if (portfolioResponse.isSuccessful && portfolioResponse.body() != null) {
            return portfolioResponse.body()!!.portfolios
        }

        if (portfolioResponse.code() == 403) {
            throw UnauthorizedException()
        }

        if (!portfolioResponse.isSuccessful) {
            throw HttpException()
        }
        return ArrayList()
    }

}