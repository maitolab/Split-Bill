package com.solid.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.solid.app.data.embedded.EmbeddedGroupWithUsers
import com.solid.app.data.entity.EntityGroup
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DaoGroup : DaoBase<EntityGroup>() {

    @Transaction
    @Query("select * from table_group")
    abstract fun getAll() : Flow<List<EmbeddedGroupWithUsers>>

    @Transaction
    @Query("select * from table_group where group_id = :groupId")
    abstract fun getGroupById(groupId: Long) : Flow<EmbeddedGroupWithUsers>
}