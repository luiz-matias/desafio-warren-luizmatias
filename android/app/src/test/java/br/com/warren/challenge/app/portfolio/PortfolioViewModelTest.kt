package br.com.warren.challenge.app.portfolio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import br.com.warren.challenge.app.exceptions.UnauthorizedException
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.data.PortfolioRepository
import br.com.warren.challenge.data.entities.Background
import br.com.warren.challenge.data.entities.Portfolio
import br.com.warren.challenge.testutils.MainCoroutineRule
import br.com.warren.challenge.testutils.getOrAwaitValue
import br.com.warren.challenge.testutils.observeForTesting
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PortfolioViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var portfolioRepository: PortfolioRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = false)
    }

    @Test
    fun `when getting portfolios, then returns a list of portfolios successfully`() =
        testDispatcher.runBlockingTest {
            //given
            portfolioRepository = mockk()

            coEvery { portfolioRepository.getPortfolio() } returns ArrayList()

            val portfolioViewModel = PortfolioViewModel(testDispatcher, portfolioRepository)

            //when
            val liveData = portfolioViewModel.portfoliosLiveData
            portfolioViewModel.getPortfolio()

            //then
            liveData.observeForTesting {
                Truth.assertThat(liveData.getOrAwaitValue())
                    .isEqualTo(Resource.Success(ArrayList<Portfolio>()))
            }

        }

    @Test
    fun `when getting portfolios and some exception occurs, then rethrow the exception trough livedata`() =
        testDispatcher.runBlockingTest {
            //given
            portfolioRepository = mockk()

            coEvery { portfolioRepository.getPortfolio() } throws UnauthorizedException()

            val portfolioViewModel = PortfolioViewModel(testDispatcher, portfolioRepository)

            //when
            val liveData = portfolioViewModel.portfoliosLiveData
            portfolioViewModel.getPortfolio()

            //then
            liveData.observeForTesting {
                Truth.assertThat((liveData.getOrAwaitValue() as Resource.Error).exception.message)
                    .isEqualTo(Resource.Error(UnauthorizedException()).exception.message)
            }

        }

    @Test
    fun `when getting portfolios, then returns the total accumulated successfully`() =
        testDispatcher.runBlockingTest {
            //given
            portfolioRepository = mockk()

            coEvery { portfolioRepository.getPortfolio() } returns arrayListOf(
                Portfolio(
                    "1",
                    "Test",
                    Background("thumb", "small", "full", "regular", "raw"),
                    BigDecimal("10.00"),
                    BigDecimal("50.00"),
                    "2019-05-10"
                ),
                Portfolio(
                    "2",
                    "Test 2",
                    Background("thumb", "small", "full", "regular", "raw"),
                    BigDecimal("20.00"),
                    BigDecimal("80.00"),
                    "2019-05-10"
                )
            )

            val portfolioViewModel = PortfolioViewModel(testDispatcher, portfolioRepository)

            //when
            val liveData: MutableLiveData<Resource<BigDecimal>> =
                portfolioViewModel.totalAccumulatedLiveData
            portfolioViewModel.getPortfolio()

            //then
            liveData.observeForTesting {
                Truth.assertThat(liveData.getOrAwaitValue())
                    .isEqualTo(Resource.Success(BigDecimal("30.00")))
            }

        }

    @Test
    fun `when getting total accumulated and some exception occurs, then rethrow the exception trough livedata`() =
        testDispatcher.runBlockingTest {
            //given
            portfolioRepository = mockk()

            coEvery { portfolioRepository.getPortfolio() } throws UnauthorizedException()

            val portfolioViewModel = PortfolioViewModel(testDispatcher, portfolioRepository)

            //when
            val liveData = portfolioViewModel.totalAccumulatedLiveData
            portfolioViewModel.getPortfolio()

            //then
            liveData.observeForTesting {
                Truth.assertThat((liveData.getOrAwaitValue() as Resource.Error).exception.message)
                    .isEqualTo(Resource.Error(UnauthorizedException()).exception.message)
            }

        }

}