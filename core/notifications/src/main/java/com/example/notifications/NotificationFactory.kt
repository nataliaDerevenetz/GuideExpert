package com.example.notifications

import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import javax.inject.Inject

abstract class Notification(val type:Int?){
    abstract fun deepLinkUri(): Uri
    abstract val title: String
    abstract val messageBody: String
}

class BookingExcursion(type:Int?, val excursionId:Int?, val profileId:Int?
) : Notification(type) {
    override val title = "Booking excursion"
    override val messageBody = "Message booking excursion"
    override fun deepLinkUri(): Uri {
        return  "$DEEP_LINK_SCHEME_AND_HOST/$DEEP_LINK_BOOKING_EXCURSION_PATH/$excursionId".toUri()
    }
}

class BookingConfirmation(type:Int?,val orderId:Int?, val isConfirmation:Boolean?) : Notification(type) {
    override val title = "Confirmation excursion"
    override val messageBody = "Message confirmation excursion"
    override fun deepLinkUri(): Uri {
        return "$DEEP_LINK_SCHEME_AND_HOST/$DEEP_LINK_BOOKING_EXCURSION_PATH/2".toUri()
    }
}

interface BaseNotificationFactory {
    fun createNotification(bundle: Bundle): Notification
}

enum class PushNotificationSource(val value: Int){
    BOOKING_EXCURSION(1), BOOKING_EXCURSION_CONFIRMATION(2);
}

class NotificationFactory @Inject constructor() : BaseNotificationFactory {
    override fun createNotification(bundle: Bundle) =
        when (bundle.getString("type")?.toInt()) {
            PushNotificationSource.BOOKING_EXCURSION.value -> BookingExcursion(
                type = bundle.getString("type")?.toInt(),
                excursionId =  bundle.getString("excursionId")?.toInt(),
                profileId = bundle.getString("profileId")?.toInt(),
            )
            PushNotificationSource.BOOKING_EXCURSION_CONFIRMATION.value -> BookingConfirmation(
                type = bundle.getString("type")?.toInt(),
                orderId = bundle.getString("orderId")?.toInt(),
                isConfirmation = bundle.getString("isConfirmation")?.toBoolean(),
            )
            else -> throw Exception()
        }
}