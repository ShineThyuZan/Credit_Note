package com.omgea.mynote.repo_impl

import com.omgea.mynote.local_db.UserDao
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
): UserRepository {
    override fun getUsers(): Flow<List<UserVo>> {
        return dao.getUsers()
    }

    override suspend fun getUserById(id: Int): UserVo? {
       return dao.getUserById(id)
    }

    override suspend fun insertUser(user: UserVo) {
        dao.insertUser(user)
    }

    override suspend fun deleteUser(user: UserVo) {
        dao.deleteUser(user)
    }
}