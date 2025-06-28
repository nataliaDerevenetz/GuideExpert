package com.example.notifications.di

import com.example.notifications.PushNotificationManager
import com.example.notifications.Notifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationsModule {
    @Binds
    abstract fun bindNotifier(
        notifier: PushNotificationManager,
    ): Notifier
}
