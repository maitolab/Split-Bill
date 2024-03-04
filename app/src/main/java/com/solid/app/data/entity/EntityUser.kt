package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "table_user")
data class EntityUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "created_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "modified_at")
    val modifiedAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "deleted_at")
    val deleteAt: LocalDateTime = LocalDateTime.now(),
)