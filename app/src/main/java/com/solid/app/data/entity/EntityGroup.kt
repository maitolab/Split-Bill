package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solid.app.domain.Category
import com.solid.app.domain.Currency
import java.time.LocalDateTime

@Entity(tableName = "table_group")
data class EntityGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "currency")
    val currency: Currency,

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "created_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "modified_at")
    val modifiedAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "deleted_at")
    val deleteAt: LocalDateTime = LocalDateTime.now(),
)