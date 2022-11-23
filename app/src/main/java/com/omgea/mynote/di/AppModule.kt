package com.omgea.mynote.di

import android.app.Application
import androidx.room.Room
import com.omgea.mynote.constant.DATABASE_NAME
import com.omgea.mynote.local_db.UserDatabase
import com.omgea.mynote.repo_impl.UserRepositoryImpl
import com.omgea.mynote.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideRepository(db: UserDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }
}