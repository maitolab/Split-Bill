package com.solid.app.data.embedded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityLinkGroupUser
import com.solid.app.data.entity.EntityUser

data class EmbeddedGroupWithUsers(
    @Embedded
    val group: EntityGroup,

    @Relation(
        parentColumn = "group_id",
        entityColumn = "user_id",
        associateBy = Junction(EntityLinkGroupUser::class)
    )
    val users: List<EntityUser>
)