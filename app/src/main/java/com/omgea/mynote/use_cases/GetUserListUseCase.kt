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


class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
){
    operator fun invoke(
        userOrder : UserOrder = UserOrder.Title(OrderType.Ascending)
    ):Flow<List<UserVo>>{
        return repository.getUsers().map { notes ->
            when(userOrder.userOrderType) {
                is OrderType.Ascending -> {
                    when(userOrder) {
                        is UserOrder.Title -> notes.sortedBy { it.name.lowercase() }
          //              is UserOrder.Date -> notes.sortedBy { it.date }
                    }
                }
                is OrderType.Descending -> {
                    when(userOrder) {
                        is UserOrder.Title -> notes.sortedBy { it.name.lowercase() }
             //           is UserOrder.Date -> notes.sortedByDescending { it.date }
                    }
                }
            }
        }
    }
}