package com.solid.app.extension

import java.text.NumberFormat
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun Double.share(numOfParticipants: Int) : Double {
    return div(numOfParticipants).roundTo(2)
}

fun Double.format() : String {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(this)
}

fun String.parseDouble() : Double {
    val numberFormat = NumberFormat.getNumberInstance()
    return try {
        numberFormat.parse(this)?.toDouble() ?: 0.0
    } catch (ex: Exception) {
        0.0
    }
}