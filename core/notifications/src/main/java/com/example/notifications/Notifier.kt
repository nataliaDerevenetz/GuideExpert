package com.example.notifications

import android.os.Bundle

interface Notifier {
    fun createNotificationFromBundle(bundle: Bundle?):Notification?
    fun postNotification(data: Map<String, String>)
   // fun createNotificationFromService(data: Map<String, String>)
}