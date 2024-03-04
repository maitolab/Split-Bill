package com.solid.app.repo.mapper

import com.solid.app.data.embedded.EmbeddedGroupWithUsers
import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityUser
import com.solid.app.domain.Category
import com.solid.app.domain.Currency
import com.solid.app.domain.Group
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupMapper @Inject constructor(
    private val userMapper: UserMapper
) {
    fun mapToEntity(group: Group) : EntityGroup {
        return EntityGroup(
            id = group.id,
            name = group.name,
            currency = group.currency,
            category = group.category
        )
    }

    fun mapToDomain(entityGroup: EntityGroup) : Group {
        return Group(
            id = entityGroup.id,
            name = entityGroup.name,
            currency = entityGroup.currency,
            category = entityGroup.category
        )
    }

    fun mapToDomain(entityGroup: EntityGroup, entityUsers: List<EntityUser>) : Group {
        return Group(
            id = entityGroup.id,
            name = entityGroup.name,
            currency = entityGroup.currency,
            category = entityGroup.category,
            users = entityUsers.map { userMapper.mapToDomain(it) }
        )
    }

    fun mapToDomain(groupWithUsers: EmbeddedGroupWithUsers?) : Group {
        return groupWithUsers?.let {
            Group(
                id = groupWithUsers.group.id,
                name = groupWithUsers.group.name,
                currency = groupWithUsers.group.currency,
                category = groupWithUsers.group.category,
                users = groupWithUsers.users.map { userMapper.mapToDomain(it) }
            )
        } ?: Group.default()
    }

}