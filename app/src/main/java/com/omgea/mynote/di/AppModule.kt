package com.omgea.mynote.di

import android.app.Application
import androidx.room.Room
import com.omgea.mynote.constant.DATABASE_NAME
import com.omgea.mynote.local_db.UserDatabase
import com.omgea.mynote.repo_impl.PasswordDsRepositoryImpl
import com.omgea.mynote.repo_impl.UserRepositoryImpl
import com.omgea.mynote.repository.PasswordDsRepository
import com.omgea.mynote.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserDatabase(app: Application) = Room.databaseBuilder(
        app,
        UserDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRepository(
        db: UserDatabase,
        @PasswordIo dispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(db.userDao, dispatcher)
    }
    @Provides
    @PasswordIo
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
interface PasswordRepository {
    @Singleton
    @Binds
    fun bindsAuthDsRepository(authDsRepo: PasswordDsRepositoryImpl): PasswordDsRepository
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class PasswordIo

