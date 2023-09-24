package com.example.presentation.main.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.presentation.R

class TaskNotifyReceiver : BroadcastReceiver() {
    private lateinit var channel: NotificationChannel

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(keyTaskDayMessage)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notify_ic)
            .setContentTitle(message)
            .setContentText(context.getString(R.string.notify_title_string))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)


        val importance = NotificationManager.IMPORTANCE_DEFAULT
        channel = NotificationChannel(channelId, name, importance).apply {
            this.description = Companion.description
        }

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).let {
            it.createNotificationChannel(channel)
            it.notify(notificationId, builder.build())
        }
    }

    companion object{
        const val name = "My Channel"
        const val description = "My Channel Description"
        const val notificationId = 1
        const val channelId = "channelId"
        const val keyTaskDayMessage = "keyTaskDayMessage"
    }
}