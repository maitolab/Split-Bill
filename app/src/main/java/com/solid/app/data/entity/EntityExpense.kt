package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solid.app.domain.Currency
import com.solid.app.domain.SplitType
import java.time.LocalDateTime

@Entity(tableName = "table_expense")
data class EntityExpense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    val id: Long = 0,

    @ColumnInfo(name = "group_id")
    val groupId: Long,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "cost")
    val cost: Double,

    @ColumnInfo(name = "currency")
    val currency: Currency,

    @ColumnInfo(name = "split_type")
    val splitType: SplitType,

    @ColumnInfo(name = "created_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "modified_at")
    val modifiedAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "deleted_at")
    val deleteAt: LocalDateTime = LocalDateTime.MIN


)