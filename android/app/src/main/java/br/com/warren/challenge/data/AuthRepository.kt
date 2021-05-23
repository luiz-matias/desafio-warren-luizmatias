package br.com.warren.challenge.data

interface AuthRepository {

    suspend fun login(email: String, password: String): Boolean

}