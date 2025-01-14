package com.example.currencyconvertcompose.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.example.currencyconvertcompose.data.models.Currency
import com.example.currencyconvertcompose.data.models.ExchangeRate
import com.example.currencyconvertcompose.data.models.SupportedCurrencyCodes
import com.example.currencyconvertcompose.util.AppDispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRatesRepository @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val database: FirebaseDatabase
) {

    val supportedCountriesFlow: Flow<List<Currency>> = flow {
        val currencies = withContext(dispatchers.default) {
            SupportedCurrencyCodes.mapNotNull { code ->
                Currency.fromCode(code).getOrNull()
            }
        }
        emit(currencies)
    }

    val exchangeRatesFlow: Flow<HashMap<String, ExchangeRate>> = callbackFlow {
        val baseCurrency = Currency.fromCode("USD").getOrThrow()
        val ref = database.getReference("conversion_rates")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exchangeRateMap = buildMap {
                    snapshot.children.mapNotNull {
                        val code = it.key ?: return@mapNotNull null
                        val currency = Currency.fromCode(code).getOrNull() ?: return@mapNotNull null
                        val value = it.getValue(Double::class.java) ?: return@mapNotNull null

                        put(
                            code, ExchangeRate(
                                baseCurrency = baseCurrency,
                                targetCurrency = currency,
                                rate = value
                            )
                        )
                    }
                }
                trySend(HashMap(exchangeRateMap))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

}
