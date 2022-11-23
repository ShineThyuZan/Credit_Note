package com.omgea.mynote.screen.edit

import com.omgea.mynote.common.FormValidator
import javax.inject.Inject


class ValidateEditUseCase @Inject constructor() {
    operator fun invoke(
        dob: Long
    ): EditError {
        val isVerifiedDob = if (dob == -1L) {
            true
        } else {
            FormValidator.isVerifiedDob(dob)
        }
        return EditError(
            isErrorDob = !isVerifiedDob
        )
    }
}