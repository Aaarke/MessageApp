package com.example.messageapp.di

import com.example.messageapp.repository.AllMessageRepository
import com.example.messageapp.repository.AllMessageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class AllMessageRepositoryModule{

    @Binds
    abstract fun bindAllMessageRepo(userServiceRepoImpl: AllMessageRepositoryImpl): AllMessageRepository
}