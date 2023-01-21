package com.omgea.mynote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.omgea.mynote.repo_impl.PasswordPrefDataStore
import com.omgea.mynote.repo_impl.PasswordPrefDataStoreImpl
import com.omgea.mynote.repo_impl.PasswordPrefDataStoreImpl.Companion.PASSWORD_PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PasswordPrefModule {
    @Provides
    @Singleton
    @PasswordPref
    fun providePasswordPref(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(PASSWORD_PREF_NAME)
            }
        )
    }

    @Provides
    @Singleton
    fun providePasswordDataStore(
        @PasswordPref ds: DataStore<Preferences>,
        @PasswordPrefIo io: CoroutineDispatcher
    ): PasswordPrefDataStore {
        return PasswordPrefDataStoreImpl(
            ds = ds,
            io = io
        )
    }

    @Provides
    @PasswordPrefIo
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class PasswordPrefIo

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PasswordPref