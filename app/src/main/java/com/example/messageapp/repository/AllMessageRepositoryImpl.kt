package com.example.messageapp.repository

import com.example.messageapp.datasource.LocalAllMessageDataSource
import com.example.messageapp.model.LocalMessage
import java.util.*
import javax.inject.Inject

class AllMessageRepositoryImpl @Inject constructor(private val localAllMessageDataSource: LocalAllMessageDataSource):AllMessageRepository {
    override fun getAllSms(): TreeMap<Long, MutableList<LocalMessage>?>? {
        return localAllMessageDataSource.getAllSms()

    }
}