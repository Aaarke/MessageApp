package com.example.messageapp.datasource

import android.content.ContentResolver
import android.provider.Telephony
import com.example.messageapp.model.LocalMessage
import java.lang.Long
import java.util.*
import javax.inject.Inject

class LocalAllMessageDataSource @Inject constructor(private val contentResolver: ContentResolver) {

    fun getAllSms(): TreeMap<kotlin.Long, MutableList<LocalMessage>?>? {
        val c = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
        val messageList= mutableListOf<LocalMessage>()
        var totalSMS = 0
        if (c != null) {
            totalSMS = c.count
            if (c.moveToFirst()) {
                for (j in 0 until totalSMS) {
                    val smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val dateFormat = Date(Long.valueOf(smsDate))
                    val timeInMillis= Long.valueOf(smsDate)
                    val diff = System.currentTimeMillis() - timeInMillis
                    val diffHours: kotlin.Long = diff / (60 * 60 * 1000) % 24
                    val type: String =
                        when (c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)).toInt()) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> "inbox"
                            Telephony.Sms.MESSAGE_TYPE_SENT -> "sent"
                            Telephony.Sms.MESSAGE_TYPE_OUTBOX -> "outbox"
                            else -> {
                                ""
                            }
                        }
                    messageList.add(LocalMessage(smsDate,number,body,dateFormat,type,diffHours))
                    c.moveToNext()
                }
            }
            c.close()
            val linkedHashMap:LinkedHashMap<kotlin.Long,MutableList<LocalMessage>?> = LinkedHashMap()
            for (message in messageList){
                if (linkedHashMap.containsKey(message.diff)){
                    val currentLis=linkedHashMap.get(message.diff)
                    currentLis?.add(message)
                    linkedHashMap[message.diff] = currentLis
                }else{
                    linkedHashMap[message.diff] = mutableListOf(message)
                }
            }
            val sorted = TreeMap<kotlin.Long, MutableList<LocalMessage>?>()
            sorted.putAll(linkedHashMap)
           return sorted
        }
        return null
    }

}