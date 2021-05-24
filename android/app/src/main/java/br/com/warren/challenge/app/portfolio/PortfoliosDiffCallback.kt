package br.com.warren.challenge.app.portfolio

import androidx.recyclerview.widget.DiffUtil
import br.com.warren.challenge.data.entities.Portfolio

class PortfoliosDiffCallback(
    private val newer: ArrayList<Portfolio>,
    private val older: ArrayList<Portfolio>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newer[newItemPosition]._id == older[oldItemPosition]._id
    }

    override fun getOldListSize(): Int {
        return older.size
    }

    override fun getNewListSize(): Int {
        return newer.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newer[newItemPosition]._id == older[oldItemPosition]._id
    }
}