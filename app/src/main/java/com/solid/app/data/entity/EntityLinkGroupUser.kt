package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "table_link_group_user",
    primaryKeys = ["group_id", "user_id"]
)
data class EntityLinkGroupUser(
    @ColumnInfo(name = "group_id")
    val groupId: Long,

    @ColumnInfo(name = "user_id")
    val userId: Long
)