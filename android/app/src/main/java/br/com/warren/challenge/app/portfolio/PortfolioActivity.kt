package br.com.warren.challenge.app.portfolio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.warren.challenge.R
import br.com.warren.challenge.app.login.LoginActivity
import br.com.warren.challenge.app.util.Resource
import br.com.warren.challenge.data.entities.Portfolio
import br.com.warren.challenge.databinding.ActivityPortfolioBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class PortfolioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioBinding
    private val viewModel: PortfolioViewModel by viewModel()

    private val adapter by lazy {
        PortfoliosAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preparePortfoliosList()
        observeViewModel()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPortfolio()
        }

        binding.imageViewLogout.setOnClickListener {
            viewModel.logout()
        }

    }

    private fun preparePortfoliosList() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewPortfolio.adapter = adapter
        binding.recyclerViewPortfolio.layoutManager = linearLayoutManager

        adapter.onClickListener = { item ->
            Toast.makeText(this, "Click em ${item.name}!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.portfoliosLiveData.observe(this, {
            when (it) {
                is Resource.Loading -> setLoading(true)
                is Resource.Error -> {
                    setLoading(false)
                    showError(it.exception)
                }
                is Resource.Success -> {
                    setLoading(false)
                    updateList(it.data)
                }
            }
        })

        viewModel.totalAccumulatedLiveData.observe(this, {
            when (it) {
                is Resource.Loading -> setLoading(true)
                is Resource.Error -> {
                    setLoading(false)
                    showError(it.exception)
                }
                is Resource.Success -> {
                    setLoading(false)
                    updateTotalAccumulated(it.data)
                }
            }
        })

        viewModel.onLogoutLiveData.observe(this, {
            if (it is Resource.Success) redirectToLogin()
        })

    }

    private fun updateList(portfolios: List<Portfolio>) {
        adapter.updateItens(ArrayList(portfolios))
    }

    private fun updateTotalAccumulated(totalValue: BigDecimal) {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        binding.textViewPortfolioDescription.text = String.format(
            Locale.getDefault(),
            getString(R.string.total_accumulated),
            format.format(totalValue)
        )
    }

    private fun showError(exception: Exception) {
        Snackbar.make(binding.root, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show()
        exception.printStackTrace()
    }

    private fun setLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
        binding.textViewPortfolioDescription.visibility =
            if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}