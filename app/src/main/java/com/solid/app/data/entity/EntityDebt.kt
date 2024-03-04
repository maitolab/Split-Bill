package com.solid.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solid.app.domain.Currency

@Entity(tableName = "table_debt")
data class EntityDebt(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "group_id")
    val groupId: Long,

    @ColumnInfo(name = "from_user_id")
    val fromUserId: Long,

    @ColumnInfo(name = "to_user_id")
    val toUserId: Long,

    @ColumnInfo(name = "currency")
    val currency: Currency,

    @ColumnInfo(name = "amount")
    val amount: Double
)