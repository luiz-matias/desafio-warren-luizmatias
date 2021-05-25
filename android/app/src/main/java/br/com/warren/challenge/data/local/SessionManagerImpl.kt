package br.com.warren.challenge.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.warren.challenge.data.entities.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionManagerImpl(private val context: Context) : SessionManager {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    override suspend fun saveAuthSession(auth: Auth) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .putString("access_token", auth.accessToken)
                .putString("refresh_token", auth.refreshToken)
                .apply()
            Log.d("SessionManagerImpl", "saveAuthSession: Saved auth session")
        }
    }

    override fun getAuthSession(): Auth? {
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("refresh_token")) {
            return Auth(
                sharedPreferences.getString("access_token", "")!!,
                sharedPreferences.getString("refresh_token", "")!!
            )
        }
        return null
    }

    override suspend fun removeAuthSession() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .remove("access_token")
                .remove("refresh_token")
                .apply()
            Log.d("SessionManagerImpl", "removeAuthSession: Removed auth session")
        }
    }
}