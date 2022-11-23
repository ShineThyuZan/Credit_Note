package com.omgea.mynote.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    private const val PROFILE_BIRTHDATE = "yyyy-MM-dd"
    //usages
    fun getCurrentMilli(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun getMilliFromDate(dateFormat: String?): Long {
        val formatter = SimpleDateFormat(PROFILE_BIRTHDATE)
        var date = Date()
        try {
            date = dateFormat?.let { formatter.parse(it) } as Date
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }
}