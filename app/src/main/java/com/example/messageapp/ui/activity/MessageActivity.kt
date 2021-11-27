package com.example.messageapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.messageapp.R
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_BODY
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_HEADER
import com.example.messageapp.ui.viewmodel.AllMessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {
   private var viewModel:AllMessageViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        viewModel=ViewModelProvider(this)[AllMessageViewModel::class.java]
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val header = intent.getStringExtra(MESSAGE_HEADER)
            val body = intent.getStringExtra(MESSAGE_BODY)

            if (header != null&& body!=null) {
                viewModel?.navigateToDetail(header,body)
            }
        }
    }
}