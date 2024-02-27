package com.omgea.mynote.common

object FormValidator {
    fun isVerifiedDob(dob: Long): Boolean {
        return (dob < DateTimeUtil.getCurrentMilli())
    }
}

