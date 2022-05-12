package uz.sanjar.note.core

import android.app.Application
import androidx.room.Room
import uz.sanjar.note.core.cache.SetUpHelper
import uz.sanjar.note.core.db.UserDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SetUpHelper.init(this)
        UserDatabase.init(this)
        db = Room.databaseBuilder(this, UserDatabase::class.java, "notes.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var db: UserDatabase? = null
    }
}