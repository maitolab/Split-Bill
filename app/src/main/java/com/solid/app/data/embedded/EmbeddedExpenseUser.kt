package com.solid.app.data.embedded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.solid.app.data.entity.EntityExpense
import com.solid.app.data.entity.EntityExpenseUser
import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityLinkGroupUser
import com.solid.app.data.entity.EntityUser

data class EmbeddedExpenseUser(
    @Embedded
    val expenseUser: EntityExpenseUser,

    @Relation(
        parentColumn = "expense_id",
        entityColumn = "expense_id",
    )
    val expense: EntityExpense,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id",
    )
    val user: EntityUser
)