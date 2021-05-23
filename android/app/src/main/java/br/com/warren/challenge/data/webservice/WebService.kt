package br.com.warren.challenge.data.webservice

import br.com.warren.challenge.data.entities.Auth
import br.com.warren.challenge.data.entities.AuthParameters
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WebService {

    @POST("account/login")
    suspend fun login(@Body authParameters: AuthParameters): Response<Auth>

}