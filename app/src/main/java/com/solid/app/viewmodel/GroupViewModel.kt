package com.solid.app.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.app.domain.Group
import com.solid.app.domain.User
import com.solid.app.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.network.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getGroup(id: Long): Flow<ResultWrapper<Group>> {
        return repository.getGroup(id)
    }

    fun getGroups(): Flow<ResultWrapper<List<Group>>> {
        return repository.getGroups()
    }

    fun createGroup(group: Group): Flow<ResultWrapper<Long>> {
        return repository.createGroup(group)
    }

    fun updateGroup(newGroup: Group): Flow<ResultWrapper<Unit>> {
        return repository.updateGroup(newGroup)
    }

    fun deleteGroup(group: Group): Flow<ResultWrapper<Unit>> {
        return repository.deleteGroup(group)
    }

    fun addUsersToGroup(groupId: Long, users: List<User>): Flow<ResultWrapper<Unit>> {
        return repository.addUsersToGroup(groupId, users)
    }
}