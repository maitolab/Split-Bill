package com.solid.app.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.network.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun addExpense(expense: Expense?, expenseUsers: List<ExpenseUser>): Flow<ResultWrapper<Unit>> {
        return repository.addExpense(expense, expenseUsers)
    }

    fun updateExpense(expense: Expense?, expenseUsers: List<ExpenseUser>): Flow<ResultWrapper<Unit>> {
        return repository.updateExpense(expense, expenseUsers)
    }

    fun getExpenses(groupId: Long) = repository.getExpenses(groupId)

    fun getExpendDetails(expenseId: Long) = repository.getExpenseDetails(expenseId)

    fun getExpenseUsers(expenseId: Long) = repository.getExpenseUsers(expenseId = expenseId)
}