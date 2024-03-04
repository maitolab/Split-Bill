package com.solid.app.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.app.domain.Transaction
import com.solid.app.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.network.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun addTransaction(transaction: Transaction) : Flow<ResultWrapper<Long>> {
        return repository.addTransaction(transaction)
    }
}