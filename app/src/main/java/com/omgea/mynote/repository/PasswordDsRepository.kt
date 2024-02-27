package com.omgea.mynote.repository

import kotlinx.coroutines.flow.Flow

interface PasswordDsRepository {
    // User Id ( put / pull )
    suspend fun putPassword(password: String)
    suspend fun pullPassword(): Flow<String>
    suspend fun putLocale( isDefault : Boolean)
    suspend fun pullLocale() : Flow<Boolean>
}