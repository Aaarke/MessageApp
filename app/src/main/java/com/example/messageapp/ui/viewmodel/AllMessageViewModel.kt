package com.example.messageapp.ui.viewmodel

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messageapp.model.LocalMessage
import com.example.messageapp.usecase.GetAllMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import extra.SingleLiveEvent
import java.lang.Long
import java.util.*
import javax.inject.Inject
import kotlin.collections.LinkedHashMap
import java.util.TreeMap




@HiltViewModel
class AllMessageViewModel  @Inject constructor(private val getAllMessageUseCase: GetAllMessageUseCase) : ViewModel() {

    val allLocalMessageList: LiveData< TreeMap<kotlin.Long, MutableList<LocalMessage>?>>
        get() = _allLocalMessageList
    private val _allLocalMessageList: MutableLiveData< TreeMap<kotlin.Long, MutableList<LocalMessage>?>> = MutableLiveData()

    val navigateToDetail: SingleLiveEvent< Pair<String,String>>
        get() = _navigateToDetail
    private val _navigateToDetail: SingleLiveEvent<Pair<String,String>> = SingleLiveEvent()


    fun getAllSms() {
        val data=getAllMessageUseCase.getAllSms()
        data?.let {
            _allLocalMessageList.postValue(it)
        }
    }

    fun navigateToDetail(header:String,body:String) {
        _navigateToDetail.postValue(Pair(header,body))
    }

}