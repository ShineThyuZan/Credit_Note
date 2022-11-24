package com.omgea.mynote.use_cases

import com.omgea.mynote.common.FormValidator
import com.omgea.mynote.screen.edit.components.EditError
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