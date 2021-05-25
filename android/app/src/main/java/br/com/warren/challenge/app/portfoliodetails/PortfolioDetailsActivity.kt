package br.com.warren.challenge.app.portfoliodetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.warren.challenge.R
import br.com.warren.challenge.data.entities.Portfolio
import br.com.warren.challenge.databinding.ActivityPortfolioDetailsBinding
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class PortfolioDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val portfolio = intent.getParcelableExtra<Portfolio>("portfolio")

        if (portfolio != null)
            setData(portfolio)
    }

    private fun setData(portfolio: Portfolio) {

        Picasso.get()
            .load(portfolio.background.regular)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(binding.imageViewBackground)

        binding.textViewTitle.text = portfolio.name

        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        binding.textViewBalance.text = format.format(portfolio.totalBalance)

        if (portfolio.goalAmount != null) {
            binding.textViewGoal.text = format.format(portfolio.goalAmount)
            binding.progressBarGoalPercentage.progressMax = portfolio.goalAmount.toFloat()
            //binding.progressBarGoalPercentage.progress = portfolio.totalBalance.toFloat()
            binding.progressBarGoalPercentage.setProgressWithAnimation(
                portfolio.totalBalance.toFloat(),
                1000,
                null,
                300
            )
            binding.textViewPercentage.text = String.format(
                Locale.getDefault(),
                "%.2f%%",
                (portfolio.totalBalance.toFloat() / portfolio.goalAmount.toFloat()) * 100
            )
        } else {
            binding.textViewGoal.text = getString(R.string.not_defined)
            binding.progressBarGoalPercentage.progressMax = 100.0f
            binding.progressBarGoalPercentage.progress = 100.0f
            binding.textViewPercentage.text = getString(R.string.not_defined)
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        binding.textViewCurrentDate.text = String.format(
            Locale.getDefault(),
            "%02d/%02d/%04d",
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(portfolio.goalDate)!!
        calendar.time = date

        binding.textViewGoalDate.text = String.format(
            Locale.getDefault(),
            "%02d/%02d/%04d",
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )

    }
}