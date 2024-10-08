package com.effective.core.di

import com.effective.core.data.HhRepositoryImpl
import com.effective.core.domain.repository.HhRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HhRepositoryModule {

    @Binds
    @Singleton
    fun bindHhRepository(impl: HhRepositoryImpl): HhRepository
}