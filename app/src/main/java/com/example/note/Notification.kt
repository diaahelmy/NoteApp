package com.example.note

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ActivityNavigator


//const val notificationid = 1
//const val channelID = "channel1"
//const val titleextra = "titleExtra"
//const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag", "MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, ActivityNavigator.Destination::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val builder = NotificationCompat.Builder(context!!, "feaxandroid")

            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(intent.getStringExtra("gggg"))
            .setContentText(intent.getStringExtra("ggg"))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())

    }
}
//        val notification = NotificationCompat.Builder(context!!, channelID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent?.getStringExtra(titleextra))
//            .setContentText(intent?.getStringExtra(messageExtra))
//            .build()
//
//
//        val manager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationid,notification)