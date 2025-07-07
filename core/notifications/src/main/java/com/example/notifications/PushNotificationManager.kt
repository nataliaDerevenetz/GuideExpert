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
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.core.utils.toBundle

const val DEEP_LINK_SCHEME_AND_HOST = "https://www.guide-expert.ru"
const val DEEP_LINK_FOR_YOU_PATH = "excursion"
const val DEEP_LINK_BASE_PATH = "$DEEP_LINK_SCHEME_AND_HOST/$DEEP_LINK_FOR_YOU_PATH"
const val ID = 1
const val TARGET_ACTIVITY_NAME = "com.example.GuideExpert.MainActivity"

const val DEEP_LINK_NEWS_RESOURCE_ID_KEY = "excursionId"
const val DEEP_LINK_URI_PATTERN = "$DEEP_LINK_BASE_PATH/{$DEEP_LINK_NEWS_RESOURCE_ID_KEY}"
//const val DEEP_LINK_URI_PATTERN = "$DEEP_LINK_BASE_PATH"


@Singleton
internal class PushNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationFactory: NotificationFactory,
) : Notifier {
    override fun createNotificationFromBundle(bundle: Bundle):Notification {
        Log.d("RRR",bundle.getString("time")!!)
        return notificationFactory.createNotification(bundle)
    }

    override fun postNotification(data: Map<String, String>) {
        sendNotification("Test",data.toBundle())
    }

    private fun sendNotification(messageBody: String, data:Bundle) = with(context) {
        //    val intent = Intent(this, MainActivity::class.java)
         val intent = Intent(this, context::class.java)
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
         intent.putExtras(data)
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
            //   .setContentIntent(pendingIntent)
               .setContentIntent(createPendingIntent(requestCode,data))


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


fun Context.createPendingIntent(requestCode: Int,bundle: Bundle?): PendingIntent? = PendingIntent.getActivity(
    this,
    requestCode,
    Intent().apply {
        action = Intent.ACTION_VIEW
        data = deepLinkUri()
        component = ComponentName(
            packageName,
            TARGET_ACTIVITY_NAME,
        )}//.putExtras(bundle!!)
    ,
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
)



//private fun deepLinkUri() = "$DEEP_LINK_BASE_PATH/$ID".toUri()

//private fun deepLinkUri() = "https://www.guide-expert.ru/test/1".toUri()
private fun deepLinkUri() = "https://www.guide-expert.ru/test".toUri()


