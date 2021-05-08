package com.bentley.ui_reservation.di

import com.bentley.ui_reservation.navigator.ReservationNavigatorImpl
import com.sullivan.sigenear.common.navigator.ReservationNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NavigationModule {
    @Binds
    abstract fun provideLoginNavigator(
        navigator: ReservationNavigatorImpl
    ): ReservationNavigator
}