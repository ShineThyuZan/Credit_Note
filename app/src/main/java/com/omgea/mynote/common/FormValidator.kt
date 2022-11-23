package com.omgea.mynote.common

import java.util.*

object FormValidator {
    fun isVerifiedDob(dob: Long): Boolean {
        return (dob < DateTimeUtil.getCurrentMilli())
    }
}

