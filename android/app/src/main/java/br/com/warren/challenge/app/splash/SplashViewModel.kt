package br.com.warren.challenge.app.splash

import androidx.lifecycle.ViewModel
import br.com.warren.challenge.data.local.SessionManager

class SplashViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun isUserLogged(): Boolean {
        return sessionManager.getAuthSession() != null
    }

}