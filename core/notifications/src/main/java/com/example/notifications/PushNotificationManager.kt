package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.app.NotificationCompat


@Singleton
internal class PushNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : Notifier {
    override fun createNotificationFromBundle(bundle: Bundle) {
        Log.d("RRR",bundle.getString("time")!!)
    }

    override fun postNotification(data: Map<String, String>) {
        sendNotification("Test")
    }

    private fun sendNotification(messageBody: String) = with(context) {
        //    val intent = Intent(this, MainActivity::class.java)
        val intent = Intent(this, context::class.java)

           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
           val requestCode = System.currentTimeMillis().toInt()//0
           val pendingIntent = PendingIntent.getActivity(
               context,
               requestCode,
               intent,
               PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
           )

           val channelId = "fcm_default_channel"
           val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
           val notificationBuilder = NotificationCompat.Builder(context, channelId)
               .setSmallIcon(R.drawable.icon_fcm)
               .setContentTitle("FCM Message")
               .setContentText(messageBody)
               .setAutoCancel(true)
               .setSound(defaultSoundUri)
               .setPriority(NotificationCompat.PRIORITY_HIGH)
               .setContentIntent(pendingIntent)

           val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

           // Since android Oreo notification channel is needed.
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               val channel = NotificationChannel(
                   channelId,
                   "Channel human readable title",
                   NotificationManager.IMPORTANCE_HIGH,
               )
               notificationManager.createNotificationChannel(channel)
           }

           val notificationId = 0
           notificationManager.notify(notificationId, notificationBuilder.build())


    }
}