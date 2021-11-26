package com.example.messageapp.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.messageapp.databinding.AllMessageFragmentBinding
import com.example.messageapp.ui.activity.MessageActivity
import com.example.messageapp.ui.viewmodel.AllMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast

import android.provider.Telephony

import android.database.Cursor

import android.content.ContentResolver
import android.content.Context
import java.lang.Long
import java.util.*


@AndroidEntryPoint
class AllMessageFragment : Fragment() {
    private var binding: AllMessageFragmentBinding?=null

    companion object {
        fun newInstance() = AllMessageFragment()
    }

    private val viewModel: AllMessageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllMessageFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }


    fun getAllSms(context: Context) {
        val cr: ContentResolver = context.contentResolver
        val c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
        var totalSMS = 0
        if (c != null) {
            totalSMS = c.count
            if (c.moveToFirst()) {
                for (j in 0 until totalSMS) {
                    val smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val dateFormat = Date(Long.valueOf(smsDate))
                    var type: String
                    when (c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)).toInt()) {
                        Telephony.Sms.MESSAGE_TYPE_INBOX -> type = "inbox"
                        Telephony.Sms.MESSAGE_TYPE_SENT -> type = "sent"
                        Telephony.Sms.MESSAGE_TYPE_OUTBOX -> type = "outbox"
                        else -> {
                        }
                    }
                    c.moveToNext()
                }
            }
            c.close()
        } else {
            //Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}