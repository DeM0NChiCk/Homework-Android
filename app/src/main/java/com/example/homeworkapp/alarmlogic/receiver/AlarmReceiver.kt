package com.example.homeworkapp.alarmlogic.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.homeworkapp.R
import kotlin.random.Random

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(NOTIFICATION_TITLE_ARG) ?: return
        val text = intent.getStringExtra(NOTIFICATION_TEXT_ARG) ?: return
        val bigText = intent.getStringExtra(NOTIFICATION_LONG_TEXT_ARG)

        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, ALARMS_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (bigText != null) {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(getNotificationId(), builder.build())

    }
    private fun getNotificationId(): Int = Random.nextInt()

    private fun createNotificationChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            ALARMS_CHANNEL_ID, ALARMS_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = ALARMS_CHANNEL_DESCRIPTION
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(notificationChannel)
    }

    companion object {
        const val NOTIFICATION_TITLE_ARG = "ntf_title"
        const val NOTIFICATION_TEXT_ARG = "ntf_text_info"
        const val NOTIFICATION_LONG_TEXT_ARG = "enable_long_text"
        const val ALARMS_CHANNEL_ID = "1337"
        const val ALARMS_CHANNEL_NAME = "Alarms"
        const val ALARMS_CHANNEL_DESCRIPTION = "In-App Alarms"
        const val ALARM_REQUEST_CODE = 0
    }
}