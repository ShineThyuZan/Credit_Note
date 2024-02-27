package com.omgea.mynote.repo_impl

import kotlinx.coroutines.flow.Flow

interface PasswordPrefDataStore{
    suspend fun putPassword(password : String)
    suspend fun pullPassword(): Flow<String>
    suspend fun putLocale(isDefault : Boolean)
    suspend fun pullLocale() : Flow<Boolean>
}