package com.solid.app.domain

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException

@Parcelize
data class Currency(
    val symbol: String,
    val code: String,
    val name: String
): Parcelable {
    companion object {
        fun default() : Currency {
            return Currency(
                symbol = "$",
                code = "USD",
                name = "US Dollar"
            )
        }
    }
}