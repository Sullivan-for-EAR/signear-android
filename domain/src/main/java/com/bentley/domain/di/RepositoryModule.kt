package com.bentley.domain.di

import com.bentley.domain.SignearRepositoryImpl
import com.bentley.domain.SignearRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun dataSource(impl: SignearRepositoryImpl): SignearRepository
}