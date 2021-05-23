package br.com.warren.challenge.app.util

/**
 * Utility class used to manage states of async requests
 */
sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}