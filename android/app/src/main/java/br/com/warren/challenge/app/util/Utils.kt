package br.com.warren.challenge.app.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun Calendar.toHumanDate(): String {
    return String.format(
        Locale.getDefault(),
        "%02d/%02d/%04d",
        this.get(Calendar.DAY_OF_MONTH),
        this.get(Calendar.MONTH) + 1,
        this.get(Calendar.YEAR)
    )
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.parse(this)!!
}

fun BigDecimal.toCurrencyString(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("BRL")
    return format.format(this)
}