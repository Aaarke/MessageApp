package com.example.messageapp.repository

import com.example.messageapp.model.LocalMessage
import java.util.*

interface AllMessageRepository {
    fun getAllSms(): TreeMap<Long, MutableList<LocalMessage>?>?
}