package br.com.warren.challenge.app.portfolio

import androidx.lifecycle.ViewModel
import br.com.warren.challenge.data.PortfolioRepository
import kotlinx.coroutines.CoroutineDispatcher

class PortfolioViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    

}