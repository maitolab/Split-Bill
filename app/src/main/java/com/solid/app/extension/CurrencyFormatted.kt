package com.solid.app.extension

import com.solid.app.domain.Currency

fun Currency.format(cost: Double) : String {
    return buildString {
        append(cost.format())
        append(" ")
        append(this@format.code)
    }
}