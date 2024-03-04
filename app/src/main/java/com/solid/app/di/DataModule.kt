package com.solid.app.di

import com.solid.app.repo.MockRepository
import com.solid.app.repo.Repository
import com.solid.app.repo.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}