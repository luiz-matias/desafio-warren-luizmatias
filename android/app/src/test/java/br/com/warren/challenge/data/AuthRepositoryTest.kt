package br.com.warren.challenge.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.warren.challenge.data.entities.Auth
import br.com.warren.challenge.data.entities.AuthParameters
import br.com.warren.challenge.data.local.SessionManager
import br.com.warren.challenge.data.webservice.WebService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
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

@RunWith(JUnit4::class)
class AuthRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var webService: WebService

    @RelaxedMockK
    lateinit var sessionManager: SessionManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = false)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when logging in succesfully, returns true`() = runBlockingTest {
        //given
        coEvery {
            webService.login(
                AuthParameters(
                    "test@test.com",
                    "123456"
                )
            )
        } returns Response.success(Auth("123", "456"))

        val authRepository = AuthRepositoryImpl(webService, sessionManager)

        //when
        val result = authRepository.login("test@test.com", "123456")

        //then
        assertThat(result).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when logging in with wrong data, returns false`() = runBlockingTest {
        //given
        val responseBody: ResponseBody =
            ("{\n" + "  \"error\": \"wrong login or password\"\n" + "}").toResponseBody("application/json".toMediaType())
        coEvery {
            webService.login(
                AuthParameters(
                    "test@test.com",
                    "123456"
                )
            )
        } returns Response.error(400, responseBody)

        val authRepository = AuthRepositoryImpl(webService, sessionManager)

        //when
        val result = authRepository.login("test@test.com", "123456")

        //then
        assertThat(result).isFalse()
    }

}