package br.com.warren.challenge.data

import br.com.warren.challenge.data.entities.AuthParameters
import br.com.warren.challenge.data.local.SessionManager
import br.com.warren.challenge.data.webservice.WebService

class AuthRepositoryImpl(
    private val webService: WebService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        val auth = webService.login(AuthParameters(email, password))
        if (auth.isSuccessful) {
            sessionManager.saveAuthSession(auth.body()!!)
            return true
        }
        return false
    }

}