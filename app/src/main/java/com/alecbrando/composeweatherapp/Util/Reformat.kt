package com.alecbrando.composeweatherapp.Util

import java.text.SimpleDateFormat
import java.util.*


 public  fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("h:mm")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

