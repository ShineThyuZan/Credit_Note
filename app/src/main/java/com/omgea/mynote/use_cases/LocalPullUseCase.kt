package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.PasswordDsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPullUseCase @Inject constructor(
    private val ds: PasswordDsRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return ds.pullLocale().map {
            it
        }
    }
}