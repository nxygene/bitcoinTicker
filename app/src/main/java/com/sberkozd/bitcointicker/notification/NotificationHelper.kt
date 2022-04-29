package com.sberkozd.bitcointicker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.sberkozd.bitcointicker.MainActivity
import com.sberkozd.bitcointicker.R

class NotificationHelper : BroadcastReceiver() {

    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val channelId = "${context.packageName}-$name"

            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description
                channel.setShowBadge(showBadge)

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun sendFavoriteCoinPriceChangedNotification(
        context: Context,
        symbol: String,
        name: String,
    ) {

        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"

        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.cryptocurrency_gold)
            setContentTitle(name.uppercase())
            setContentText(symbol)
            setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(R.string.fav_coin_price_changed)
                )
            )
            priority = NotificationCompat.PRIORITY_HIGH
            setAutoCancel(true)

            val bundle = Bundle()
            bundle.putString("symbol", symbol)

            val pendingIntent = NavDeepLinkBuilder(context)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.my_nav)
                .setDestination(R.id.detailFragment)
                .setArguments(bundle)
                .createPendingIntent()

            setContentIntent(pendingIntent)
        }
        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1001, notificationBuilder.build())
    }

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}