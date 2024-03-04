package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException

@Parcelize
sealed class Category(val id: String): Parcelable {

    data object Trip: Category("trip")
    data object Couple: Category("couple")
    data object Shopping: Category("shopping")
    data object Food: Category("food")
    data object Sports: Category("sports")
    data object Games: Category("games")
    data object Kids: Category("kids")
    data object Others: Category("others")

    companion object {
        fun supportedCategories() = listOf(
            Trip,
            Couple,
            Shopping,
            Food,
            Sports,
            Games,
            Kids,
            Others
        )

        fun default() = Others

        fun from(id: String): Category {
            return supportedCategories().find { it.id == id } ?: throw IllegalArgumentException("Unknown category id $id")
        }
    }
}