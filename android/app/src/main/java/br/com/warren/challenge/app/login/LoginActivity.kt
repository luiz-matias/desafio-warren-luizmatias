package br.com.warren.challenge.app.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.warren.challenge.R
import br.com.warren.challenge.app.exceptions.InvalidEmailException
import br.com.warren.challenge.app.exceptions.InvalidPasswordException
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUiActions()
    }

    private fun setupUiActions() {
        binding.buttonLogin.setOnClickListener {
            startLogin(
                binding.textInputEditTextEmail.text.toString(),
                binding.textInputEditTextPassword.text.toString()
            )
        }
    }

    private fun startLogin(email: String, password: String) {
        setEmailInvalid(false)
        setPasswordInvalid(false)
        viewModel.login(email, password).observe(this, {
            when (it) {
                is Resource.Success -> {
                    setLoading(false)
                    if(it.data) {
                        redirectToHome()
                    } else {
                        showInvalidLoginError()
                    }
                }
                is Resource.Error -> {
                    setLoading(false)
                    when (it.exception) {
                        is InvalidEmailException -> setEmailInvalid(true)
                        is InvalidPasswordException -> setPasswordInvalid(true)
                        else -> showUnknownError(it.exception)
                    }
                }
                is Resource.Loading -> setLoading(true)
            }
        })
    }

    private fun showInvalidLoginError() {
        Snackbar.make(this, binding.root, getString(R.string.invalid_login), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showUnknownError(exception: Exception) {
        Snackbar.make(this, binding.root, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun setEmailInvalid(isInvalid: Boolean) {
        if (isInvalid) {
            binding.textInputEditTextEmail.error = getString(R.string.invalid_email)
        }
        binding.textInputLayoutEmail.isErrorEnabled = isInvalid
    }

    private fun setPasswordInvalid(isInvalid: Boolean) {
        if (isInvalid) {
            binding.textInputEditTextPassword.error = getString(R.string.invalid_password)
        }
        binding.textInputLayoutPassword.isErrorEnabled = isInvalid
    }

    private fun redirectToHome() {
        //startActivity(Intent(this, SplashActivity::class.java))
        //finish()
        Toast.makeText(this, "Login feito!", Toast.LENGTH_SHORT).show()
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBarLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.buttonLogin.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }
}