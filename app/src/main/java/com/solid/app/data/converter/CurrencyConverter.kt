package com.solid.app.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.solid.app.domain.Currency
import javax.inject.Inject

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return Gson().toJson(currency)
    }

    @TypeConverter
    fun toCurrency(json: String): Currency {
        return Gson().fromJson(json, Currency::class.java)
    }
}