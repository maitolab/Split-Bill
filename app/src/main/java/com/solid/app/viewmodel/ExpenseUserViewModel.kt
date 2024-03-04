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
class ExpenseUserViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun addExpenseUser(expenseUser: ExpenseUser): Flow<ResultWrapper<Long>> {
        return repository.addExpenseUser(expenseUser)
    }
}