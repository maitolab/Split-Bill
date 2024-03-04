package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_expense_user")
data class EntityExpenseUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "expense_id")
    var expenseId: Long,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "paid_share")
    var paidShare: Double = 0.0,

    @ColumnInfo(name = "ownedShare")
    var ownedShare: Double = 0.0
)