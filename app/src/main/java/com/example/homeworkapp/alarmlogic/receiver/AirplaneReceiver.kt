package com.example.homeworkapp.alarmlogic.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneReceiver(
    val callback: (Boolean) -> Unit
): BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            callback(
                p1.getBooleanExtra("state", false)
            )
        }
    }

}