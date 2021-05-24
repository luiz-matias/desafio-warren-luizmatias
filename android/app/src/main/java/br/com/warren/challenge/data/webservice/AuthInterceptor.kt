package br.com.warren.challenge.data.webservice

import br.com.warren.challenge.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class interceptor applies all the necessary Auth headers through all network requests
 */
class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val auth = sessionManager.getAuthSession()
        if (auth != null) {
            val request = original.newBuilder()
                .header("access-token", auth.accessToken)
                .method(original.method, original.body)
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(original)
    }
}