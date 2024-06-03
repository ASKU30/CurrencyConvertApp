package com.example.currencyconvertcompose.data.models

import androidx.compose.runtime.Immutable
import com.example.currencyconvertcompose.data.models.Currency

@Immutable
data class ExchangeRate(
    val baseCurrency: Currency,
    val targetCurrency: Currency,
    val rate: Double
)
