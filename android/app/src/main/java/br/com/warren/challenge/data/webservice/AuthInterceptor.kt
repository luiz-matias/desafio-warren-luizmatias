package br.com.warren.challenge.data.webservice

import br.com.warren.challenge.data.local.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class interceptor applies all the necessary Auth headers through all network requests.
 * Also, this class will automatically delete the auth headers when response code is 403
 */
class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val auth = sessionManager.getAuthSession()

        val response: Response = if (auth != null) {
            val request = original.newBuilder()
                .header("access-token", auth.accessToken)
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        } else {
            chain.proceed(original)
        }

        if (response.code == 403) {
            CoroutineScope(Dispatchers.IO).launch {
                sessionManager.removeAuthSession()
            }
        }
        return response
    }
}