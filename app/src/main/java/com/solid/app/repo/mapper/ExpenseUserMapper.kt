package com.solid.app.repo.mapper

import com.solid.app.data.embedded.EmbeddedExpenseUser
import com.solid.app.data.entity.EntityExpenseUser
import com.solid.app.domain.ExpenseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseUserMapper @Inject constructor(
    private val expenseMapper: ExpenseMapper,
    private val userMapper: UserMapper
) {
    fun mapToEntity(expenseUser: ExpenseUser) : EntityExpenseUser {
        return EntityExpenseUser(
            userId = expenseUser.user.id,
            expenseId = expenseUser.expense.id,
            paidShare = expenseUser.paidShare,
            ownedShare = expenseUser.ownedShare
        )
    }

    fun mapToDomain(entity: EmbeddedExpenseUser) : ExpenseUser {
        return ExpenseUser(
            id = entity.expenseUser.id,
            expense = expenseMapper.mapToDomain(entity.expense),
            user = userMapper.mapToDomain(entity.user),
            paidShare = entity.expenseUser.paidShare,
            ownedShare = entity.expenseUser.ownedShare
        )
    }
}