package com.solid.app.data.dao

import androidx.room.Dao
import com.solid.app.data.entity.EntityDebt
import com.solid.app.data.entity.EntityUser

@Dao
abstract class DaoDebt : DaoBase<EntityDebt>() {
}