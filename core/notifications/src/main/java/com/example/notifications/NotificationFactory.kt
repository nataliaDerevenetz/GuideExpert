package com.example.notifications

import android.os.Bundle
import javax.inject.Inject

abstract class Notification(val type:Int?)

class BookingExcursion(type:Int?, val excursionId:Int?, val profileId:Int?) : Notification(type)
class BookingConfirmation(type:Int?,val orderId:Int?, val isConfirmation:Boolean?) : Notification(type)

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