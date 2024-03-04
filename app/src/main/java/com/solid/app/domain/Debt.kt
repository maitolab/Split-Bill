package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Debt(
    val group: Group,
    val fromUser: User,
    val toUser: User,
    val amount: Double,
    val currency: Currency
): Parcelable