package com.example.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class PushNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : Notifier {
    override fun createNotificationFromBundle(bundle: Bundle) {
        Log.d("RRR",bundle.getString("time")!!)
    }
}