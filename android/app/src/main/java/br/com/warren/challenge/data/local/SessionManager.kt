package br.com.warren.challenge.data.local

import br.com.warren.challenge.data.entities.Auth

interface SessionManager {
    suspend fun saveAuthSession(auth: Auth)
    fun getAuthSession(): Auth?
}