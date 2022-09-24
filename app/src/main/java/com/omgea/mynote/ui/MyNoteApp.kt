package com.omgea.mynote.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.Configuration
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyNoteApp : MultiDexApplication(), Configuration.Provider {

    companion object {
        const val UPLOAD_CHANNEL = "upload.channel"
        const val CHANNEL_NAME = "upload.photo.files"
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                UPLOAD_CHANNEL,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun getWorkManagerConfiguration(): Configuration {
        return if (BuildConfig.DEBUG) {
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .build()
        } else {
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(android.util.Log.ERROR)
                .build()
        }
    }

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        val name = NotificationConstants.CHANNEL_NAME
        val description = NotificationConstants.CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NotificationConstants.CHANNEL_ID,name,importance)
        channel.description = description

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)

    }

    val builder = NotificationCompat.Builder(context,NotificationConstants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Downloading your file...")
        .setOngoing(true)
        .setProgress(0,0,true)

    NotificationManagerCompat.from(context).notify(NotificationConstants.NOTIFICATION_ID,builder.build())*/
}