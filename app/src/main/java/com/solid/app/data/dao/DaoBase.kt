package com.solid.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class DaoBase<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(entity: Entity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveAll(entities: List<Entity>)

    @Update
    abstract suspend fun update(entity: Entity)

    @Delete
    abstract suspend fun delete(entity: Entity)
}