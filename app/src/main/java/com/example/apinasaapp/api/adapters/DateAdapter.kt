package com.example.apinasaapp.api.adapters

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    @SuppressLint("SimpleDateFormat")
    @FromJson
    fun toDate(timeStr: String) : Date{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeStr) ?: Date()
    }

    @ToJson
    fun fromDate(date: Date): String{
        return date.time.toString()
    }
}