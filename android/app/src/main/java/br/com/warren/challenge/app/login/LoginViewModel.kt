package br.com.warren.challenge.app.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.warren.challenge.app.exceptions.InvalidEmailException
import br.com.warren.challenge.app.exceptions.InvalidPasswordException
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.data.AuthRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)

        if (!isEmailValid(email)) {
            emit(Resource.Error(InvalidEmailException()))
        }

        if (!isPasswordValid(password)) {
            emit(Resource.Error(InvalidPasswordException()))
        }

        if (!isEmailValid(email) || !isPasswordValid(password)) {
            return@liveData
        }

        try {
            emit(Resource.Success(authRepository.login(email, password)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e))
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

}