package com.solid.app.repo.mapper

import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityUser
import com.solid.app.domain.Group
import com.solid.app.domain.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper @Inject constructor() {
    fun mapToEntity(user: User): EntityUser {
        return EntityUser(
            name = user.name
        )
    }

    fun mapToDomain(entityUser: EntityUser): User {
        return User(
            id = entityUser.id,
            name = entityUser.name
        )
    }
}