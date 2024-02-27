package com.omgea.mynote.repo_impl

import com.omgea.mynote.di.PasswordIo
import com.omgea.mynote.repository.PasswordDsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordDsRepositoryImpl @Inject constructor(
    private val pwPref: PasswordPrefDataStore,
    @PasswordIo private val io: CoroutineDispatcher,
) : PasswordDsRepository {
    override suspend fun putPassword(password: String) {
        withContext(io) {
            pwPref.putPassword(password = password)
        }
    }

    override suspend fun pullPassword(): Flow<String> {
        return pwPref.pullPassword().flowOn(context = io)
    }

    override suspend fun putLocale(isDefault: Boolean) {
        withContext(io) {
            pwPref.putLocale(isDefault = isDefault)
        }
    }

    override suspend fun pullLocale(): Flow<Boolean> {
        return pwPref.pullLocale().flowOn(context = io)
    }
}