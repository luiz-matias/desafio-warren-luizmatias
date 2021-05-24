package br.com.warren.challenge.app.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.warren.challenge.data.entities.Auth
import br.com.warren.challenge.data.local.SessionManager
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplashViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @MockK
    lateinit var sessionManager: SessionManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = false)
    }

    @Test
    fun `when auth headers is not null, then returns true`() {

        //given
        every { sessionManager.getAuthSession() } returns Auth("someValue", "someOtherValue")

        val splashViewModel = SplashViewModel(sessionManager)

        //when
        val result = splashViewModel.isUserLogged()

        //then
        Truth.assertThat(result).isTrue()

    }

    @Test
    fun `when auth headers is null, then returns false`() {

        //given
        every { sessionManager.getAuthSession() } returns null

        val splashViewModel = SplashViewModel(sessionManager)

        //when
        val result = splashViewModel.isUserLogged()

        //then
        Truth.assertThat(result).isFalse()

    }

}