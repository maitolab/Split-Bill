package com.solid.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.solid.app.data.embedded.EmbeddedExpense
import com.solid.app.data.embedded.EmbeddedExpenseUser
import com.solid.app.data.entity.EntityExpense
import com.solid.app.data.entity.EntityExpenseUser
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DaoExpense : DaoBase<EntityExpense>() {

    @Query("select * from table_expense where group_id = :groupId")
    abstract fun getAll(groupId: Long): Flow<List<EntityExpense>>

    @Query("select * from table_expense where expense_id = :expenseId")
    abstract fun getExpense(expenseId: Long): Flow<EmbeddedExpense>

    @Transaction
    @Query("select * from table_expense_user where expense_id = :expenseId")
    abstract fun getExpenseUsers(expenseId: Long): Flow<List<EmbeddedExpenseUser>>
}