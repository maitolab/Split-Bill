package com.solid.app.factory

import com.solid.app.composables.TabInfo
import com.solid.app.domain.Category
import com.solid.app.domain.Currency
import com.solid.app.domain.Expense
import com.solid.app.domain.Group
import com.solid.app.domain.SplitType
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class Mock @Inject constructor() {
    fun group(): Group {
        return Group(
            id = Random.nextLong(),
            name = "Test ${UUID.randomUUID().toString().take(2)}",
            users = emptyList(),
            currency = Currency.default(),
            category = Category.Trip
        )
    }

    fun expense() : Expense {
        return Expense(
            id = Random.nextLong(),
            group = group(),
            description = "Expense description",
            cost = 10000.0,
            currency = Currency.default(),
            createAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now(),
            deletedAt = LocalDateTime.now(),
            splitType = SplitType.Equally
        )
    }
}