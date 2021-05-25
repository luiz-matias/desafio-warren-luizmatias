package br.com.warren.challenge.app.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Converts an calendar instance into a String with the format "dd/mm/yyyy"
 */
fun Calendar.toHumanDate(): String {
    return String.format(
        Locale.getDefault(),
        "%02d/%02d/%04d",
        this.get(Calendar.DAY_OF_MONTH),
        this.get(Calendar.MONTH) + 1,
        this.get(Calendar.YEAR)
    )
}

/**
 * Converts a String with the format "yyyy-mm-dd" into a date instance
 */
fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.parse(this)!!
}

/**
 * Convert a big decimal instance value into a currency string with the BRL format. Example: "R$13,000.00"
 */
fun BigDecimal.toCurrencyString(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("BRL")
    return format.format(this)
}