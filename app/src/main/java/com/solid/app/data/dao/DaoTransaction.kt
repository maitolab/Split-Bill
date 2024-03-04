package com.solid.app.data.dao

import androidx.room.Dao
import com.solid.app.data.entity.EntityTransaction
import com.solid.app.data.entity.EntityUser

@Dao
abstract class DaoTransaction : DaoBase<EntityTransaction>() {
}