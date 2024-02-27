package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.UserRepository
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.util.OrderType
import com.omgea.mynote.util.UserOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
class GetUserListUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<UserVo>> {
        return repository.getUsers()
    }
}


