package com.example.messageapp.model

import java.util.*


data class LocalMessage( val smsDate:String, val number:String, val body:String, val dateFormat:Date, val type:String,val diff:Long)
