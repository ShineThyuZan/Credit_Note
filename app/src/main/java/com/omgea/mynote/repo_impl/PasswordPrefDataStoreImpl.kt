package com.omgea.mynote.repo_impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.omgea.mynote.di.PasswordPref
import com.omgea.mynote.di.PasswordPrefIo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PasswordPrefDataStoreImpl @Inject constructor(
    @PasswordPref private val ds: DataStore<Preferences>,
    @PasswordPrefIo private val io: CoroutineDispatcher,
) : PasswordPrefDataStore {

    companion object {
        const val PASSWORD_PREF_NAME = "password.ds.pref"
        val PASSWORD = stringPreferencesKey("my_note.password")
        const val DEFAULT_PASSWORD = "0000"
    }

    override suspend fun putPassword(password: String) {
        withContext(io) {
            ds.edit {
                it[PASSWORD] = password
            }
        }
    }

    override suspend fun pullPassword(): Flow<String> {
        return ds.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[PASSWORD] ?: DEFAULT_PASSWORD
        }.flowOn(io)
    }

}