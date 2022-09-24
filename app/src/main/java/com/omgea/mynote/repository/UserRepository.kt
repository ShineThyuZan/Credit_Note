package com.omgea.mynote.repository

import com.omgea.mynote.model.UserVo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<UserVo>>

    suspend fun getUserById(id: Int): UserVo?

    suspend fun insertUser(user: UserVo)

    suspend fun deleteUser(user: UserVo)
}