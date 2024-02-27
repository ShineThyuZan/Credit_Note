package com.omgea.mynote.use_cases

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(
    context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "APP_LOCALE")
    private val dataStore = context.dataStore

    companion object {
        val myanmarLocaleKey = booleanPreferencesKey("MYANMAR_LOCALE_KEY")
    }

    suspend fun setLocale(isMyanmarLocale: Boolean) {
        dataStore.edit { pref ->
            pref[myanmarLocaleKey] = isMyanmarLocale
        }
    }

    fun getLocale(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { pref ->
                val uiMode = pref[myanmarLocaleKey] ?: false
                uiMode
            }
    }
}