package com.solid.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.solid.app.data.entity.EntityExpenseUser

@Dao
abstract class DaoExpenseUser : DaoBase<EntityExpenseUser>() {

    @Query("delete from table_expense_user where expense_id = :expenseId")
    abstract suspend fun deleteByExpenseId(expenseId: Long)
}