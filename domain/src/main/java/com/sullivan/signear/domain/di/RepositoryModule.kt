package com.sullivan.signear.domain.di

import com.sullivan.signear.domain.SignearRepository
import com.sullivan.signear.domain.SignearRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun dataSource(impl: SignearRepositoryImpl): SignearRepository
}