package br.com.warren.challenge.data.local

import br.com.warren.challenge.data.entities.Auth

/**
 * Class responsible to manage the session of the current user
 */
interface SessionManager {

    /**
     * Saves the Auth session in the storage
     */
    suspend fun saveAuthSession(auth: Auth)

    /**
     * Get the current auth session
     */
    fun getAuthSession(): Auth?

    /**
     * Removes the auth session from the app
     */
    suspend fun removeAuthSession()
}