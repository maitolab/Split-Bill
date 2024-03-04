package com.solid.app.repo.mapper

import com.solid.app.data.entity.EntityDebt
import com.solid.app.domain.Debt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebtMapper @Inject constructor() {
    fun mapToEntity(debt: Debt) : EntityDebt {
        return EntityDebt(
            groupId = debt.group.id,
            fromUserId = debt.fromUser.id,
            toUserId = debt.toUser.id,
            currency = debt.currency,
            amount = debt.amount
        )
    }
}