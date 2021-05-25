package br.com.warren.challenge.app.portfolio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.warren.challenge.R
import br.com.warren.challenge.data.entities.Portfolio
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class PortfoliosAdapter(private val context: Context) :
    RecyclerView.Adapter<PortfoliosAdapter.ViewHolder>() {

    private val portfolios: ArrayList<Portfolio> = ArrayList()
    var onClickListener: (portfolio: Portfolio) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.portfolio_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val portfolio = portfolios[position]

        Picasso.get()
            .load(portfolio.background.small)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(holder.imageViewBackground)

        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")

        holder.textViewTitle.text = portfolio.name
        holder.textViewBalance.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.balance),
            format.format(portfolio.totalBalance)
        )

        if (portfolio.goalAmount != null) {
            holder.textViewGoal.text = String.format(
                Locale.getDefault(),
                context.getString(R.string.goal),
                format.format(portfolio.goalAmount)
            )

            holder.progressBarGoalPercentage.visibility = View.VISIBLE
            holder.progressBarGoalPercentage.progress =
                ((portfolio.totalBalance.toDouble() / portfolio.goalAmount.toDouble()) * 100).roundToInt()
        } else {
            holder.progressBarGoalPercentage.visibility = View.INVISIBLE
            holder.textViewGoal.text = context.getString(R.string.goal_not_defined)
        }

        holder.cardViewPortfolio.setOnClickListener {
            onClickListener(portfolio)
        }
    }

    override fun getItemCount(): Int {
        return portfolios.size
    }

    fun updateItens(portfolios: ArrayList<Portfolio>) {
        val diffResult = DiffUtil.calculateDiff(
            PortfoliosDiffCallback(
                newer = portfolios,
                older = this.portfolios
            )
        )
        this.portfolios.clear()
        this.portfolios.addAll(portfolios)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewPortfolio: CardView = itemView.findViewById(R.id.cardViewPortfolio)
        val imageViewBackground: ImageView = itemView.findViewById(R.id.imageViewBackground)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewBalance: TextView = itemView.findViewById(R.id.textViewBalance)
        val textViewGoal: TextView = itemView.findViewById(R.id.textViewGoal)
        val progressBarGoalPercentage: ProgressBar =
            itemView.findViewById(R.id.progressBarGoalPercentage)
    }

}
