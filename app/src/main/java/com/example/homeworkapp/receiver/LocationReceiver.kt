package com.example.homeworkapp.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast
import com.example.homeworkapp.service.MyLocationService

class LocationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (isTrackingServiceRunning(context)) return

        if (intent?.action == LocationManager.MODE_CHANGED_ACTION) {
            if (!intent.getBooleanExtra(LocationManager.EXTRA_LOCATION_ENABLED, true)) {
                stopTrackingService(context)
                return
            }
        }
    }

    private fun stopTrackingService(context: Context) {
        Toast.makeText(context, "Tracking stopped", Toast.LENGTH_LONG).show()
        context.stopService(MyLocationService.getServiceIntent(context))
    }


    @Suppress("DEPRECATION")
    private fun isTrackingServiceRunning(context: Context): Boolean {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val services: List<ActivityManager.RunningServiceInfo> =
            activityManager.getRunningServices(Int.MAX_VALUE)

        for (runningServiceInfo in services) {
            if (runningServiceInfo.service.className == MyLocationService::class.java.name) {
                return true
            }
        }

        return false
    }
}