package com.solid.app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException

@Parcelize
sealed class SplitType(val id: String) : Parcelable {
    data object Equally : SplitType("equally")
    data object Unequally : SplitType("unequally")
    data object Percentage : SplitType("percentage")

    companion object {
        fun from(id: String): SplitType {
            return listOf(
                Equally,
                Unequally,
                Percentage
            ).find { it.id == id } ?: throw IllegalArgumentException("Unknown split type id $id")
        }
    }
}
