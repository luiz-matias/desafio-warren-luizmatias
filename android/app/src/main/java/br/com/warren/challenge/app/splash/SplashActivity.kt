package br.com.warren.challenge.app.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.warren.challenge.R
import br.com.warren.challenge.app.login.LoginActivity
import br.com.warren.challenge.app.portfolio.PortfolioActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            if (viewModel.isUserLogged()) {
                startActivity(Intent(this@SplashActivity, PortfolioActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }

            finish()

        }, 2000)

    }
}