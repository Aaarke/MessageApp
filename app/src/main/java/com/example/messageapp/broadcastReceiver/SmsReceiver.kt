package com.example.messageapp.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast
import java.util.regex.Pattern
import android.app.PendingIntent

import android.R

import android.app.Notification
import android.app.NotificationChannel

import androidx.core.content.ContextCompat.getSystemService

import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.messageapp.ui.activity.MessageActivity
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_BODY
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_HEADER
import javax.inject.Inject


class SmsReceiver  : BroadcastReceiver() {
    var p: Pattern = Pattern.compile("(|^)\\d{6}")
    override fun onReceive(context: Context?, intent: Intent) {
        if(context == null || intent == null || intent.action == null){
            return
        }
        if (intent.action != (Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            return
        }
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (message in smsMessages) {
            Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : body ${message.messageBody}", Toast.LENGTH_SHORT)
                .show()

            showNotification(context,message.displayOriginatingAddress,message.messageBody)

    }


}

    private fun showNotification(context: Context,title:String,body:String) {
        val packageName = context.packageName

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.WHITE
            }
            channel.description = packageName
            channel.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channel)
            val notificationBuilder = NotificationCompat.Builder(context, packageName)
            notificationBuilder
                .setSmallIcon(R.drawable.btn_minus)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true).setContentTitle(title)
                .setContentText(body)


            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra(MESSAGE_HEADER, title)
            intent.putExtra(MESSAGE_BODY, body)
            intent.setClass(context, MessageActivity::class.java)

            val backIntent = Intent(context.applicationContext, MessageActivity::class.java)
            val notificationId =1
            val pendingIntent = PendingIntent.getActivities(
                context.applicationContext,
                notificationId,
                arrayOf(backIntent, intent),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.setContentIntent(pendingIntent)
            mNotificationManager.notify(
                notificationId,
                notificationBuilder.build()
            )

        }

    }
}