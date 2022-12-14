package com.example.homeworkapp.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.homeworkapp.R
import kotlin.random.Random


class MyLocationService : Service(), LocationListener {

    private val notificationId = Random.nextInt()
    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onLocationChanged(loc: Location) {
        notificationManager.notify(notificationId, getNotification(loc.toNotificationText()))
    }

    override fun onCreate() {
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q)
            startForeground(
                notificationId,
                getNotification(NOTIFICATION_START_TEXT),
                FOREGROUND_SERVICE_TYPE_LOCATION
            )
        else
            startForeground(notificationId, getNotification(NOTIFICATION_START_TEXT))

        setLocationUpdateListener()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        removeLocationUpdateListener()
    }

    private fun removeLocationUpdateListener() {
        locationManager.removeUpdates(this)
    }

    private fun setLocationUpdateListener() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("LocationService", "Location permission is not granted")
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_UPDATE_INTERVAL,
            LOCATION_UPDATE_DISTANCE,
            this
        )
    }

    private fun getNotification(text: String) =
        NotificationCompat.Builder(this, LOCATION_TRACKER_CHANNEL_ID)
            .setContentTitle("Location Service").setContentText(text)
            .setSmallIcon(R.drawable.ic_baseline_not_listed_location_24).build()

    private fun createNotificationChannel() {
        NotificationChannel(LOCATION_TRACKER_CHANNEL_ID,
            LOCATION_TRACKER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH).apply {
            description = LOCATION_TRACKER_CHANNEL_DESCRIPTION
        }.also { notificationManager.createNotificationChannel(it) }
    }

    private fun Location.toNotificationText(): String {
        with(StringBuilder()) {
            append("Longitude: $longitude, Latitude: $latitude")
            if (hasAltitude()) append(", Altitude: $altitude")
            return toString()
        }
    }

    companion object {
        const val NOTIFICATION_START_TEXT = "Location Service is running"
        const val LOCATION_TRACKER_CHANNEL_ID = "1337"
        const val LOCATION_TRACKER_CHANNEL_NAME = "Location Tracker"
        const val LOCATION_TRACKER_CHANNEL_DESCRIPTION = "Displays location of the user"
        const val LOCATION_UPDATE_INTERVAL = 1000L
        const val LOCATION_UPDATE_DISTANCE = 0f

        fun getServiceIntent(context: Context) = Intent(context, MyLocationService::class.java)
    }
}