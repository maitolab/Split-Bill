package com.solid.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.solid.app.data.converter.CategoryConverter
import com.solid.app.data.converter.CurrencyConverter
import com.solid.app.data.converter.DateTimeConverter
import com.solid.app.data.converter.SplitTypeConverter
import com.solid.app.data.dao.DaoDebt
import com.solid.app.data.dao.DaoExpense
import com.solid.app.data.dao.DaoExpenseUser
import com.solid.app.data.dao.DaoGroup
import com.solid.app.data.dao.DaoLinkGroupUser
import com.solid.app.data.dao.DaoTransaction
import com.solid.app.data.dao.DaoUser
import com.solid.app.data.entity.EntityDebt
import com.solid.app.data.entity.EntityExpense
import com.solid.app.data.entity.EntityExpenseUser
import com.solid.app.data.entity.EntityGroup
import com.solid.app.data.entity.EntityLinkGroupUser
import com.solid.app.data.entity.EntityTransaction
import com.solid.app.data.entity.EntityUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        EntityGroup::class,
        EntityUser::class,
        EntityExpense::class,
        EntityExpenseUser::class,
        EntityTransaction::class,
        EntityDebt::class,
        EntityLinkGroupUser::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    DateTimeConverter::class,
    SplitTypeConverter::class,
    CategoryConverter::class,
    CurrencyConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private lateinit var sInstance: AppDatabase

        fun create(context: Context, migrations: List<Migration> = emptyList()): AppDatabase {
            if (!this::sInstance.isInitialized) {
                sInstance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "spliser.db"
                )
                    .addMigrations(*migrations.toTypedArray())
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sInstance
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    // Insert categories

                    // Insert currencies
                }
            }
        }
    }

    abstract fun daoLinkGroupUser(): DaoLinkGroupUser
    abstract fun daoGroup(): DaoGroup
    abstract fun daoUser(): DaoUser
    abstract fun daoTransaction(): DaoTransaction
    abstract fun daoExpense(): DaoExpense
    abstract fun daoExpenseUser(): DaoExpenseUser
    abstract fun daoDebt(): DaoDebt

    @Transaction
    suspend fun addUserToGroup(groupId: Long, user: EntityUser) {
        daoLinkGroupUser().save(
            entity = EntityLinkGroupUser(
                groupId = groupId,
                userId = daoUser().save(user)
            )
        )
    }

    @Transaction
    suspend fun addUsersToGroup(groupId: Long, users: List<EntityUser>) {
        users.forEach {
            addUserToGroup(groupId, it)
        }
    }
}