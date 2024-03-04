package com.solid.app.repo

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solid.app.data.AppDatabase
import com.solid.app.domain.Currency
import com.solid.app.domain.Debt
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.domain.Group
import com.solid.app.domain.Transaction
import com.solid.app.domain.User
import com.solid.app.repo.mapper.DebtMapper
import com.solid.app.repo.mapper.ExpenseMapper
import com.solid.app.repo.mapper.ExpenseUserMapper
import com.solid.app.repo.mapper.GroupMapper
import com.solid.app.repo.mapper.TransactionMapper
import com.solid.app.repo.mapper.UserMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.network.ResultWrapper
import io.chipmango.network.flowApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: AppDatabase,
    private val groupMapper: GroupMapper,
    private val userMapper: UserMapper,
    private val expenseMapper: ExpenseMapper,
    private val expenseUserMapper: ExpenseUserMapper,
    private val transactionMapper: TransactionMapper,
    private val debtMapper: DebtMapper
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

    override fun getSupportedCurrencies(): List<Currency> {
        return currencies
    }

    override fun getCurrencyFromCode(code: String): Currency {
        return currencies.find { it.code == code } ?: Currency.default()
    }

    override fun getGroup(id: Long): Flow<ResultWrapper<Group>> {
        return database.daoGroup().getGroupById(id)
            .map {
                groupMapper.mapToDomain(it)
            }.map {
                ResultWrapper.success(it)
            }
    }

    override fun createGroup(group: Group) = flowApiCall {
        database.daoGroup().save(
            entity = groupMapper.mapToEntity(group)
        )
    }

    override fun getGroups(): Flow<ResultWrapper<List<Group>>> {
        return database.daoGroup().getAll()
            .map { groupList ->
                groupList.map {
                    groupMapper.mapToDomain(it)
                }
            }.map {
                ResultWrapper.success(it)
            }
    }

    override fun updateGroup(newGroup: Group) = flowApiCall {
        database.daoGroup().update(groupMapper.mapToEntity(newGroup))
    }

    override fun deleteGroup(group: Group) = flowApiCall {
        database.daoGroup().delete(groupMapper.mapToEntity(group))
    }

    override fun addUserToGroup(groupId: Long, user: User) = flowApiCall {
        database.addUserToGroup(
            groupId = groupId,
            user = userMapper.mapToEntity(user)
        )
    }

    override fun addUsersToGroup(groupId: Long, user: List<User>) = flowApiCall {
        database.addUsersToGroup(
            groupId = groupId,
            users = user.map { userMapper.mapToEntity(it) }
        )
    }

    @androidx.room.Transaction
    override fun addExpense(expense: Expense?, expenseUsers: List<ExpenseUser>) = flowApiCall {
        if (expense != null) {
            val expenseId = database.daoExpense().save(
                entity = expenseMapper.mapToEntity(expense)
            )
            database.daoExpenseUser().saveAll(
                entities = expenseUsers.map {
                    expenseUserMapper.mapToEntity(it).copy(
                        expenseId = expenseId
                    )
                }
            )
        }
    }

    @androidx.room.Transaction
    override fun updateExpense(expense: Expense?, expenseUsers: List<ExpenseUser>) = flowApiCall {
        if (expense != null) {
            database.daoExpense().update(entity = expenseMapper.mapToEntity(expense))

            database.daoExpenseUser().deleteByExpenseId(expense.id)

            database.daoExpenseUser().saveAll(
                entities = expenseUsers.map {
                    expenseUserMapper.mapToEntity(it).copy(
                        expenseId = expense.id
                    )
                }
            )
        }
    }

    override fun addExpenseUser(expenseUser: ExpenseUser) = flowApiCall {
        database.daoExpenseUser().save(
            entity = expenseUserMapper.mapToEntity(expenseUser)
        )
    }

    override fun addTransaction(transaction: Transaction) = flowApiCall {
        database.daoTransaction().save(
            entity = transactionMapper.mapToEntity(transaction)
        )
    }

    override fun addDebt(debt: Debt) = flowApiCall {
        database.daoDebt().save(
            entity = debtMapper.mapToEntity(debt)
        )
    }

    override fun getExpenses(groupId: Long): Flow<ResultWrapper<List<Expense>>> {
        return database.daoExpense().getAll(groupId)
            .map { expenseList ->
                expenseList.map {
                    expenseMapper.mapToDomain(it)
                }
            }
            .map {
                ResultWrapper.success(it)
            }
    }

    override fun getExpenseDetails(expenseId: Long): Flow<ResultWrapper<Expense>> {
        return database.daoExpense().getExpense(expenseId)
            .map {
                expenseMapper.mapToDomain(it)
            }
            .map {
                ResultWrapper.success(it)
            }
    }

    override fun getExpenseUsers(expenseId: Long): Flow<ResultWrapper<List<ExpenseUser>>> {
        return database.daoExpense().getExpenseUsers(expenseId)
            .map { list ->
                list.map { expenseUserMapper.mapToDomain(it) }
            }
            .map {
                ResultWrapper.success(it)
            }
    }
}
