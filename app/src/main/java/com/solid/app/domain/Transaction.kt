package com.solid.app.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long = 0,
    val group: Group,
    val fromUser: User,
    val toUser: User,
    val currency: Currency,
    val cost: Double
): Parcelable