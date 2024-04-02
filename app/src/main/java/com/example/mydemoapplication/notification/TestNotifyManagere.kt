package com.example.mydemoapplication.notification

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mydemoapplication.ScrollingActivity


object NotifyManager {

    fun create(context: Context) {
        createNotificationChannel(context)

        // 步骤1 - 创建通知构建器
        // 步骤1 - 创建通知构建器
        val mBuilder = NotificationCompat.Builder(context,"channel_id")

        // 步骤2 - 设置通知属性

        // 步骤2 - 设置通知属性
        mBuilder.setSmallIcon(R.mipmap.sym_def_app_icon)
        mBuilder.setContentTitle("通知标题")
        mBuilder.setContentText("您好，这是Android通知的详细信息！")

        // 步骤3 - 添加操作

        // 步骤3 - 添加操作

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
//        val resultIntent = Intent(context, ScrollingActivity::class.java)
//        val resultPendingIntent =
//            PendingIntent.getActivity(context, 0, resultIntent, pendingIntentFlags)
//        mBuilder.setContentIntent(resultPendingIntent)

        // 步骤4 - 发出通知

        // 步骤4 - 发出通知
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        mNotificationManager?.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }

    // 步骤1 - 创建通知渠道
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name"
            val description = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelId= "channel_id"
            val channel = NotificationChannel(channelId, name, importance)
                .apply {
                    this.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    this.setShowBadge(true)
                }
            channel.description = description
            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.getNotificationChannel(channelId).let {
                if (it == null) {
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }
}