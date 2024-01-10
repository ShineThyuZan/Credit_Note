package com.omgea.mynote.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object MathUtils {

    private const val SECOND = 1
    private const val MINUTE = 60 * SECOND
    private const val HOUR = 60 * MINUTE
    private const val DAY = 24 * HOUR
    private const val MONTH = 30 * DAY
    private const val YEAR = 12 * MONTH

    fun convertCreatedDate(timeLong: Long): Pair<String, String> {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timeLong
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        if (days > 7) {
            val inputYear = convertLongToTime(timeLong)
            val currentYear = convertLongToTime(currentTime)
            return if (inputYear == currentYear) {
                Pair(convertLongToCurrentYearFormat(timeLong), "No")
            } else {
                Pair(convertLongToFullDateFormat(timeLong), "No")
            }
        }
        if (hours >= 24) {
            return Pair(days.toString(), "Day")
        }
        if (minutes >= 60) {
            return Pair(hours.toString(), "Hour")
        }
        return if (minutes < 1) {
            Pair("", "Just now")
        } else {
            return Pair(minutes.toString(), "Minute")
        }
    }

    private fun currentDate(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    fun timeAgo(input: Long): String {
        val now = currentDate()
        // convert back to second
        val diff = (now - input) / 1000

        return when {
            diff < MINUTE -> "Just now"
            diff < 2 * MINUTE -> "a minute ago"
            diff < 60 * MINUTE -> "${diff / MINUTE} minutes ago"
            diff < 2 * HOUR -> "an hour ago"
            diff < 24 * HOUR -> "${diff / HOUR} hours ago"
            diff < 2 * DAY -> "yesterday"
            diff < 30 * DAY -> "${diff / DAY} days ago"
            diff < 2 * MONTH -> "a month ago"
            diff < 12 * MONTH -> "${diff / MONTH} months ago"
            diff < 14 * MONTH -> "1 year ago"  // Adjusted condition for 1 year
            else -> {
                val years = diff / YEAR
                val months = (diff % YEAR) / MONTH
                if (months > 0) {
                    "$years years and $months months ago"
                } else {
                    "$years years ago"
                }
            }
        }
    }

    fun timeAgoDateAndFormat(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timestamp
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30.44 // Average days in a month
        val years = days / 365.25 // Average days in a year, considering leap years
        return when {
            seconds < 60 -> "Just now"
            minutes == 1L -> "1 min ago"
            minutes < 60 -> "$minutes mins ago"
            hours == 1L -> "1 hr ago"
            hours < 24 -> "$hours hrs ago"
            days == 1L -> "1 day ago"
            days < 7 -> "$days days ago"
            weeks == 1L -> "1 week ago"
            weeks < 4 -> "$weeks weeks ago"
            months == 1.0 -> "1 month ago"
            months < 12 -> "$months months ago"
            years == 1.0 -> "1 year ago"
            else -> "$years years ago"
        }
    }

    fun factorial(n: Int): Int {
        if (n == 0 || n == 1) {
            return 1
        }
        return n * factorial(n - 1)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToCurrentYearFormat(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MMM")
        return format.format(date)
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToFullDateFormat(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MMM yyyy")
        return format.format(date)
    }
    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }
    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd")
        return df.parse(date)?.time ?: 0L
    }
}