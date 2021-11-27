package com.example.messageapp.usecase

import com.example.messageapp.repository.AllMessageRepository
import javax.inject.Inject

class GetAllMessageUseCase @Inject constructor(private val allMessageRepository: AllMessageRepository) {
    fun getAllSms() = allMessageRepository.getAllSms()
}