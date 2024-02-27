package com.omgea.mynote.local_db

import androidx.room.*
import com.omgea.mynote.model.UserVo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserVo")
    fun getUsers(): Flow<List<UserVo>>

    @Query("SELECT * FROM UserVo")
    fun getUsersNotFlow(): List<UserVo>

    @Query("SELECT * FROM UserVo WHERE id = :id")
    suspend fun getUserById(id: Int): UserVo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserVo)

    @Delete
    suspend fun deleteUser(user: UserVo)
}