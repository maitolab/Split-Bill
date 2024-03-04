package com.solid.app.repo

import com.solid.app.domain.Currency
import com.solid.app.domain.Debt
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.domain.Group
import com.solid.app.domain.Transaction
import com.solid.app.domain.User
import io.chipmango.network.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getSupportedCurrencies(): List<Currency>
    fun getCurrencyFromCode(code: String): Currency
    fun getGroup(id: Long): Flow<ResultWrapper<Group>>
    fun getGroups(): Flow<ResultWrapper<List<Group>>>
    fun updateGroup(newGroup: Group): Flow<ResultWrapper<Unit>>
    fun deleteGroup(group: Group): Flow<ResultWrapper<Unit>>
    fun createGroup(group: Group): Flow<ResultWrapper<Long>>
    fun addUserToGroup(groupId: Long, user: User): Flow<ResultWrapper<Unit>>
    fun addUsersToGroup(groupId: Long, user: List<User>): Flow<ResultWrapper<Unit>>

    fun addExpense(expense: Expense?, expenseUsers: List<ExpenseUser>): Flow<ResultWrapper<Unit>>
    fun updateExpense(expense: Expense?, expenseUsers: List<ExpenseUser>): Flow<ResultWrapper<Unit>>
    fun addExpenseUser(expenseUser: ExpenseUser): Flow<ResultWrapper<Long>>
    fun addTransaction(transaction: Transaction): Flow<ResultWrapper<Long>>
    fun addDebt(debt: Debt): Flow<ResultWrapper<Long>>

    fun getExpenses(groupId: Long) : Flow<ResultWrapper<List<Expense>>>
    fun getExpenseDetails(expenseId: Long): Flow<ResultWrapper<Expense>>
    fun getExpenseUsers(expenseId: Long): Flow<ResultWrapper<List<ExpenseUser>>>
}