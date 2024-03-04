package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseUser(
    val id: Long = 0,
    val expense: Expense,
    val user: User,
    val paidShare: Double,
    val ownedShare: Double
): Parcelable {

    fun getNetBalance(): Balance {
        val expenseCost = expense.cost
        return Balance(
            cost = (paidShare - ownedShare) * expenseCost,
            currency = expense.currency
        )
    }

}