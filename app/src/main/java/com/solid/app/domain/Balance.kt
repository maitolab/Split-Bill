package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Balance(
    val cost: Double,
    val currency: Currency
): Parcelable