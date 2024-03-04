package com.solid.app.repo.mapper

import com.solid.app.data.embedded.EmbeddedExpense
import com.solid.app.data.entity.EntityExpense
import com.solid.app.domain.Expense
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(
    private val groupMapper: GroupMapper
) {
    fun mapToEntity(expense: Expense): EntityExpense {
        return EntityExpense(
            id = expense.id,
            groupId = expense.group!!.id,
            description = expense.description,
            cost = expense.cost,
            currency = expense.currency,
            splitType = expense.splitType,
            createAt = expense.createAt,
            modifiedAt = expense.modifiedAt,
            deleteAt = expense.deletedAt
        )
    }

    fun mapToDomain(entity: EmbeddedExpense): Expense {
        return Expense(
            id = entity.expense.id,
            cost = entity.expense.cost,
            description = entity.expense.description,
            createAt = entity.expense.createAt,
            modifiedAt = entity.expense.modifiedAt,
            deletedAt = entity.expense.deleteAt,
            splitType = entity.expense.splitType,
            currency = entity.expense.currency,
            group = groupMapper.mapToDomain(entity.group, entity.groupUsers)
        )
    }

    fun mapToDomain(entityExpense: EntityExpense): Expense {
        return Expense(
            id = entityExpense.id,
            cost = entityExpense.cost,
            description = entityExpense.description,
            createAt = entityExpense.createAt,
            modifiedAt = entityExpense.modifiedAt,
            deletedAt = entityExpense.deleteAt,
            splitType = entityExpense.splitType,
            currency = entityExpense.currency
        )
    }
}