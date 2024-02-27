package com.omgea.mynote.data.repository

import com.omgea.mynote.model.UserVo
import com.omgea.mynote.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private val users = mutableListOf<UserVo>()
    override fun getUsers(): Flow<List<UserVo>> {
        return flow { emit( users) }
    }

    override suspend fun getUsersNotFlow(): List<UserVo> {
        return users
    }

    override suspend fun getUserById(id: Int): UserVo? {
        return users.find { it.id == id }
    }

    override suspend fun insertUser(user: UserVo) {
        users.add(user)
    }

    override suspend fun deleteUser(user: UserVo) {
        users.remove(user)
    }
}