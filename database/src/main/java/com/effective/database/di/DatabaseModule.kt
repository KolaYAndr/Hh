package com.effective.database.di

import android.content.Context
import com.effective.database.data.local.dao.VacancyDao
import com.effective.database.data.local.db.VacancyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): VacancyDatabase =
            VacancyDatabase.getDatabase(context)

        @Singleton
        @Provides
        fun provideDao(db: VacancyDatabase): VacancyDao = db.vacancyDao()
    }
}
