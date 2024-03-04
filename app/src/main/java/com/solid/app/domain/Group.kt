package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: Long = 0,
    val name: String,
    val currency: Currency,
    val category: Category,
    val users: List<User> = emptyList()
): Parcelable {
    companion object {
        fun default() : Group {
            return Group(
                name = "",
                currency = Currency.default(),
                category = Category.default(),
                users = emptyList()
            )
        }
    }
}