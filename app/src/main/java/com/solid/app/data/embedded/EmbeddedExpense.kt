package com.solid.app.data.embedded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.solid.app.data.entity.EntityExpense
import com.solid.app.data.entity.EntityExpenseUser
import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityLinkGroupUser
import com.solid.app.data.entity.EntityUser

data class EmbeddedExpense(
    @Embedded
    val expense: EntityExpense,

    @Relation(
        parentColumn = "group_id",
        entityColumn = "group_id",
    )
    val group: EntityGroup,

    @Relation(
        parentColumn = "group_id",
        entityColumn = "user_id",
        associateBy = Junction(EntityLinkGroupUser::class)
    )
    val groupUsers: List<EntityUser>
)