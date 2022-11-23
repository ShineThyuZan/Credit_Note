package com.omgea.mynote.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omgea.mynote.model.UserVo

@Database(
    entities = [UserVo::class],
    version = 5,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}