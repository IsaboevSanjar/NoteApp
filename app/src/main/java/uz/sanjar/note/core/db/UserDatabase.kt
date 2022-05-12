@file:OptIn(InternalCoroutinesApi::class)

package uz.sanjar.note.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import uz.sanjar.note.core.db.dao.MainDOA
import uz.sanjar.note.core.model.Notes


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): MainDOA

    companion object {
        private var INSTANCE: UserDatabase? = null
        private val database: UserDatabase? = null
        fun init(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "notes.db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        fun getInstance(): UserDatabase? = INSTANCE
    }
}