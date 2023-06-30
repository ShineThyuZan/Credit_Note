package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.UserRepository
import com.omgea.mynote.model.UserVo
import javax.inject.Inject
class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: UserVo) {
        repository.deleteUser(user)
    }
}