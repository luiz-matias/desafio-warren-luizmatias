package br.com.warren.challenge.data

/**
 * Repository responsible for managing the auth of the user
 */
interface AuthRepository {

    /**
     * Method responsible to Authenticate the user
     */
    suspend fun login(email: String, password: String): Boolean

}