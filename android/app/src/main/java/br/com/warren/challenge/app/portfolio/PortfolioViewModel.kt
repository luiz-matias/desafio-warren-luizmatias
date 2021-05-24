package br.com.warren.challenge.app.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.data.PortfolioRepository
import br.com.warren.challenge.data.entities.Portfolio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PortfolioViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    val portfoliosLiveData = MutableLiveData<Resource<List<Portfolio>>>()
    val totalAccumulatedLiveData = MutableLiveData<Resource<BigDecimal>>()

    init {
        getPortfolio()
    }

    fun getPortfolio() {
        viewModelScope.launch(coroutineDispatcher) {

            portfoliosLiveData.postValue(Resource.Loading)
            totalAccumulatedLiveData.postValue(Resource.Loading)

            try {
                val portfolios = portfolioRepository.getPortfolio()

                portfoliosLiveData.postValue(Resource.Success(portfolios))

                var totalAccumulated = BigDecimal("0.00")
                portfolios.forEach {
                    totalAccumulated = totalAccumulated.add(it.totalBalance)
                }

                totalAccumulatedLiveData.postValue(Resource.Success(totalAccumulated))

            } catch (e: Exception) {
                portfoliosLiveData.postValue(Resource.Error(e))
                totalAccumulatedLiveData.postValue(Resource.Error(e))
            }
        }
    }

}