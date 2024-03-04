package com.solid.app.repo

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solid.app.domain.Currency
import com.solid.app.domain.Debt
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.domain.Group
import com.solid.app.domain.Transaction
import com.solid.app.domain.User
import com.solid.app.factory.Mock
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.network.ResultWrapper
import io.chipmango.network.flowApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mock: Mock
) : Repository {
    private val currencies by lazy {
        try {
            val json = context.assets.open("currencies.json").bufferedReader().use { it.readText() }
            val type = TypeToken.getParameterized(List::class.java, Currency::class.java).type
            Gson().fromJson<List<Currency>?>(json, type).sortedBy { it.name }
        } catch (ex: Exception) {
            Timber.e(ex)
            emptyList()
        }
    }

    override fun getGroups(): Flow<ResultWrapper<List<Group>>> {
        return flow { emit((0..20).map { mock.group() }) }.map { ResultWrapper.success(it) }
    }

    override fun getSupportedCurrencies(): List<Currency> {
        return currencies
    }

    override fun getCurrencyFromCode(code: String): Currency {
        return currencies.find { it.code == code } ?: Currency.default()
    }

    override fun getGroup(id: Long): Flow<ResultWrapper<Group>> {
        return flow { emit(mock.group()) }.map { ResultWrapper.success(it) }
    }

    override fun createGroup(group: Group): Flow<ResultWrapper<Long>> {
        return flow { emit(ResultWrapper.success(0L)) }
    }

    override fun addUserToGroup(groupId: Long, user: User): Flow<ResultWrapper<Unit>> {
        return flow { }
    }

    override fun addUsersToGroup(groupId: Long, user: List<User>): Flow<ResultWrapper<Unit>> {
        return flow { }
    }

    override fun updateGroup(newGroup: Group): Flow<ResultWrapper<Unit>> {
        return flow {  }
    }

    override fun deleteGroup(group: Group): Flow<ResultWrapper<Unit>> {
        return flow {  }
    }

    override fun addExpense(expense: Expense?, expenseUsers: List<ExpenseUser>): Flow<ResultWrapper<Unit>> {
        return flow { }
    }

    override fun updateExpense(
        expense: Expense?,
        expenseUsers: List<ExpenseUser>
    ): Flow<ResultWrapper<Unit>> {
        TODO("Not yet implemented")
    }

    override fun addExpenseUser(expenseUser: ExpenseUser): Flow<ResultWrapper<Long>> {
        TODO("Not yet implemented")
    }

    override fun addTransaction(transaction: Transaction): Flow<ResultWrapper<Long>> {
        TODO("Not yet implemented")
    }

    override fun addDebt(debt: Debt): Flow<ResultWrapper<Long>> {
        TODO("Not yet implemented")
    }

    override fun getExpenses(groupId: Long): Flow<ResultWrapper<List<Expense>>> {
        return flow { emit((0..20).map { mock.expense() }) }.map { ResultWrapper.success(it) }
    }

    override fun getExpenseDetails(expenseId: Long): Flow<ResultWrapper<Expense>> {
        TODO("Not yet implemented")
    }

    override fun getExpenseUsers(expenseId: Long): Flow<ResultWrapper<List<ExpenseUser>>> {
        TODO("Not yet implemented")
    }
}