package com.frogobox.base.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.frogobox.base.R
import com.frogobox.base.util.Constant.Constant.PACKAGE_ROOT
import com.frogobox.base.util.Constant.Constant.PATH_MAIN_ACTIVITY

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * movie
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.base.service
 *
 */
class Notification {

    fun sendNotification(
        context: Context,
        notificationId: Int,
        channelId: String?,
        channelName: CharSequence?,
        contentTitle: String?,
        contentText: String?
    ) {

        val intent = Intent(Intent.ACTION_MAIN)
        intent.component = ComponentName(PACKAGE_ROOT, PATH_MAIN_ACTIVITY)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = channelId.let {
            it?.let { it1 ->
                NotificationCompat.Builder(context, it1)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.ic_notification
                        )
                    )
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSound(alarmSound)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channelId.let { it?.let { it1 -> mBuilder?.setChannelId(it1) } }
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder?.build()
        mNotificationManager.notify(notificationId, notification)

    }
}
