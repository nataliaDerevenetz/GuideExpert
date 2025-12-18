package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.core.utils.toBundle
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val DEEP_LINK_SCHEME_AND_HOST = "https://www.guide-expert.ru"
const val DEEP_LINK_BOOKING_EXCURSION_PATH = "excursiondetail"
const val TARGET_ACTIVITY_NAME = "com.example.GuideExpert.MainActivity"

@Singleton
internal class PushNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationFactory: NotificationFactory,
) : Notifier {
    override fun createNotificationFromBundle(bundle: Bundle?): Notification? {
        if (bundle == null) return null
        Log.d("RRR",bundle.getString("time")!!)
        return notificationFactory.createNotification(bundle)
    }

    override fun postNotification(data: Map<String, String>) {
        sendNotification(data.toBundle())
    }

    private fun sendNotification(data:Bundle) = with(context) {

        val notification = createNotificationFromBundle(data)
        if(notification == null)  return
        val requestCode = System.currentTimeMillis().toInt()

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_fcm)
            .setContentTitle(notification.title)
            .setContentText(notification.messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(createPendingIntent(requestCode,notification))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel notification",
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}


fun Context.createPendingIntent(requestCode: Int,notification: Notification): PendingIntent? = PendingIntent.getActivity(
    this,
    requestCode,
    Intent().apply {
        action = Intent.ACTION_VIEW
        data = notification.deepLinkUri()
        component = ComponentName(
            packageName,
            TARGET_ACTIVITY_NAME,
        )}
    ,
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
)


