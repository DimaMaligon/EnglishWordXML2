package com.example.englishwordxml2.di

import android.app.Application
import com.example.englishwordxml2.db.MyDbManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMyDbManager(application: Application): MyDbManager {
        return MyDbManager(application)
    }
}