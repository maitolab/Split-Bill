package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Expense(
    val id: Long = 0,
    val group: Group? = null,
    val description: String,
    val cost: Double,
    val currency: Currency,
    val splitType: SplitType,
    val createAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val deletedAt: LocalDateTime
) : Parcelable