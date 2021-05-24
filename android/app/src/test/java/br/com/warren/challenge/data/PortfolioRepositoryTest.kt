package br.com.warren.challenge.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.warren.challenge.app.exceptions.HttpException
import br.com.warren.challenge.app.exceptions.UnauthorizedException
import br.com.warren.challenge.data.entities.Portfolio
import br.com.warren.challenge.data.entities.PortfolioResponse
import br.com.warren.challenge.data.webservice.WebService
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PortfolioRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var webService: WebService

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetching portfolios into the happy path, returns a portfolio's list`() =
        runBlockingTest {
            //given
            coEvery { webService.getPortfolio() } returns Response.success(
                PortfolioResponse(
                    ArrayList()
                )
            )

            val portfolioRepository = PortfolioRepositoryImpl(webService)

            //when
            val result = portfolioRepository.getPortfolio()

            //then
            Truth.assertThat(result).isEqualTo(ArrayList<Portfolio>())
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetching portfolios without auth permission, returns an exception`() =
        runBlockingTest {
            //given
            val responseBody: ResponseBody =
                ("{\n" + "  \"error\": \"unauthorized\"\n" + "}").toResponseBody("application/json".toMediaType())
            coEvery { webService.getPortfolio() } returns Response.error(403, responseBody)

            val portfolioRepository = PortfolioRepositoryImpl(webService)

            try {
                //when
                portfolioRepository.getPortfolio()
            } catch (e: Exception) {
                //then
                Truth.assertThat(e.message).isEqualTo(UnauthorizedException().message)
            }

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetching portfolios and some unknown error occurs, returns an exception`() =
        runBlockingTest {
            //given
            val responseBody: ResponseBody =
                ("{\n" + "  \"error\": \"what a terrible error\"\n" + "}").toResponseBody("application/json".toMediaType())
            coEvery { webService.getPortfolio() } returns Response.error(500, responseBody)

            val portfolioRepository = PortfolioRepositoryImpl(webService)

            try {
                //when
                portfolioRepository.getPortfolio()
            } catch (e: Exception) {
                //then
                Truth.assertThat(e.message).isEqualTo(HttpException().message)
            }

        }

}