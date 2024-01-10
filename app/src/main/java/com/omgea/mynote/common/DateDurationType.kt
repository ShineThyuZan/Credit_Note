package com.omgea.mynote.common

sealed interface DateDurationType {
    data class Year(val value: Long) : DateDurationType
    data class Month(val value: Long) : DateDurationType
    data class Week(val value: Long) : DateDurationType
    data class Day(val value: Long) : DateDurationType
    data class Hour(val value: Long) : DateDurationType
    data class Minute(val value: Long) : DateDurationType
}
