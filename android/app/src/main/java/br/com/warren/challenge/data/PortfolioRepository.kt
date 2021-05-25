package br.com.warren.challenge.data

import br.com.warren.challenge.data.entities.Portfolio

/**
 * Repository responsible to manage all portfolios of the user
 */
interface PortfolioRepository {

    /**
     * Method responsible to return all the portfolios from the user
     */
    suspend fun getPortfolio(): List<Portfolio>

}