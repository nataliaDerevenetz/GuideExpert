package com.example.notifications

import android.os.Bundle

interface Notifier {
    fun createNotificationFromBundle(bundle: Bundle)
   // fun createNotificationFromService(data: Map<String, String>)
}