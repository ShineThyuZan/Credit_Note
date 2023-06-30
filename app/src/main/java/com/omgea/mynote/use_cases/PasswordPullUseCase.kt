package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.PasswordDsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class PasswordPullUseCase @Inject constructor(
    private val ds: PasswordDsRepository
) {
    suspend operator fun invoke(): Flow<String> {
        return ds.pullPassword()
    }
}