package br.com.warren.challenge.data.webservice

import br.com.warren.challenge.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class interceptor applies all the necessary Auth headers through all network requests
 */
class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val auth = sessionManager.getAuthSession()
        if (auth != null) {
            val headers = req.headers.newBuilder().add("access_token", auth.accessToken).build()
            req = req.newBuilder().headers(headers).build()
        }
        return chain.proceed(req)
    }
}