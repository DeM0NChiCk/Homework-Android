package com.example.homeworkapp.alarmlogic.setter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import com.example.homeworkapp.alarmlogic.receiver.AlarmReceiver
import com.example.homeworkapp.alarmlogic.receiver.AlarmReceiver.Companion.NOTIFICATION_LONG_TEXT_ARG
import com.example.homeworkapp.alarmlogic.receiver.AlarmReceiver.Companion.NOTIFICATION_TEXT_ARG
import com.example.homeworkapp.alarmlogic.receiver.AlarmReceiver.Companion.NOTIFICATION_TITLE_ARG

class AlarmSetter (
    private val context:Context
) {
    fun setAlarm(
        title: String,
        text: String,
        delaySeconds: Long,
        bigText: String? = null
    ){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + (delaySeconds * 1000),
            getAlarmPendingIntent(title, text, bigText)
        )
    }

    fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(getAlarmPendingIntent(null, null, null))
    }

    private fun getAlarmPendingIntent(
        title: String?,
        text: String?,
        bigText: String?
    ): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)

        title?.let {
            intent.putExtra(
                NOTIFICATION_TITLE_ARG,
                title
            )
        }
        text?.let {
            intent.putExtra(
                NOTIFICATION_TEXT_ARG,
                text
            )
        }
        bigText?.let {
            intent.putExtra(
                NOTIFICATION_LONG_TEXT_ARG,
                bigText
            )
        }

        return PendingIntent.getBroadcast(
            context,
            AlarmReceiver.ALARM_REQUEST_CODE,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}