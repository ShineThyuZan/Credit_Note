package com.omgea.mynote.use_cases

import com.omgea.mynote.model.UserVo
import com.omgea.mynote.repository.UserRepository
import javax.inject.Inject
class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: UserVo) {
        repository.insertUser(user)
    }
}