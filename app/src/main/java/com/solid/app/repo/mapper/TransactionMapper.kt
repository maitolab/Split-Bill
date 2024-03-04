package com.solid.app.repo.mapper

import com.solid.app.data.entity.EntityTransaction
import com.solid.app.data.entity.EntityUser
import com.solid.app.domain.Transaction
import com.solid.app.domain.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionMapper @Inject constructor() {
    fun mapToEntity(transaction: Transaction) : EntityTransaction {
        return EntityTransaction(
            groupId = transaction.group.id,
            fromUserId = transaction.fromUser.id,
            toUserId = transaction.toUser.id,
            currency = transaction.currency,
            cost = transaction.cost
        )
    }
}