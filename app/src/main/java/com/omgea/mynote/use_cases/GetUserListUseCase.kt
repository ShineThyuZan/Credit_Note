package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.UserRepository
import com.omgea.mynote.model.UserVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<UserVo>> {
        return repository.getUsers()
    }
}