package com.example.currencyconvertcompose

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconvertcompose.data.models.Currency
import com.example.currencyconvertcompose.data.dao.FavoriteCurrenciesDao

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class CurrencyConverterDatabase : RoomDatabase() {
    abstract fun favoriteCurrenciesDao(): FavoriteCurrenciesDao
}