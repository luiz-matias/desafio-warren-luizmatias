package br.com.warren.challenge.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import br.com.warren.challenge.app.exceptions.InvalidEmailException
import br.com.warren.challenge.app.exceptions.InvalidPasswordException
import br.com.warren.challenge.app.login.LoginViewModel
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.data.AuthRepository
import br.com.warren.challenge.testutils.MainCoroutineRule
import br.com.warren.challenge.testutils.getOrAwaitValue
import br.com.warren.challenge.testutils.observeForTesting
import com.google.common.truth.Truth.assertThat
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

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = false)
    }

    @Test
    fun `when login called with happy path, then returns Success`() =
        testDispatcher.runBlockingTest {
            //given
            authRepository = mockk()

            coEvery { authRepository.login("test@test.com", "123456") } returns true

            val loginViewModel = LoginViewModel(testDispatcher, authRepository)

            //when
            val liveData = loginViewModel.login("test@test.com", "123456")

            //then
            liveData.observeForTesting {
                assertThat(liveData.getOrAwaitValue()).isEqualTo(Resource.Success(true))
            }

        }

    @Test
    fun `when login called with wrong email, then returns Exception Error`() =
        testDispatcher.runBlockingTest {
            //given
            authRepository = mockk()

            coEvery { authRepository.login("test@test.com", "123456") } returns true

            val loginViewModel = LoginViewModel(testDispatcher, authRepository)

            //when
            val liveData: LiveData<Resource<Boolean>> =
                loginViewModel.login("invalid@email", "123456")

            //then
            liveData.observeForTesting {
                assertThat((liveData.getOrAwaitValue() as Resource.Error).exception.message).isEqualTo(
                    Resource.Error(InvalidEmailException()).exception.message
                )
            }

        }

    @Test
    fun `when login called with wrong password, then returns Exception Error`() =
        testDispatcher.runBlockingTest {
            //given
            authRepository = mockk()

            coEvery { authRepository.login("test@test.com", "123456") } returns true

            val loginViewModel = LoginViewModel(testDispatcher, authRepository)

            //when
            val liveData: LiveData<Resource<Boolean>> =
                loginViewModel.login("test@test.com", "123")

            //then
            liveData.observeForTesting {
                assertThat((liveData.getOrAwaitValue() as Resource.Error).exception.message).isEqualTo(
                    Resource.Error(InvalidPasswordException()).exception.message
                )
            }

        }

    @Test
    fun `when login called with invalid login into WS, then returns Exception Error`() =
        testDispatcher.runBlockingTest {
            //given
            authRepository = mockk()

            coEvery { authRepository.login("test@test.com", "123456") } returns false

            val loginViewModel = LoginViewModel(testDispatcher, authRepository)

            //when
            val liveData: LiveData<Resource<Boolean>> =
                loginViewModel.login("test@test.com", "123456")

            //then
            liveData.observeForTesting {
                assertThat(liveData.getOrAwaitValue()).isEqualTo(Resource.Success(false))
            }

        }

    @Test
    fun `when login called and an unknown error occurs, rethrow exception into resource class`() =
        testDispatcher.runBlockingTest {
            //given
            authRepository = mockk()

            coEvery {
                authRepository.login(
                    "test@test.com",
                    "123456"
                )
            } throws Exception("Unknown exception")

            val loginViewModel = LoginViewModel(testDispatcher, authRepository)

            //when
            val liveData: LiveData<Resource<Boolean>> =
                loginViewModel.login("test@test.com", "123456")

            //then
            liveData.observeForTesting {
                assertThat((liveData.getOrAwaitValue() as Resource.Error).exception.message).isEqualTo(
                    Resource.Error(Exception("Unknown exception")).exception.message
                )
            }

        }

}