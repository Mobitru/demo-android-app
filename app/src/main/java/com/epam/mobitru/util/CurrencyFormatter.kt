package com.epam.mobitru.util

import java.text.NumberFormat
import java.util.*

object CurrencyFormatter {
    private val currencyInstance = NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 0
    }

    fun formatPrice(value: Double): String {
        return currencyInstance.format(value)
    }

}