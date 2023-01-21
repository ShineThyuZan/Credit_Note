package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.PasswordDsRepository
import javax.inject.Inject

class PasswordPutUseCase @Inject constructor(
    private val ds: PasswordDsRepository
) {
    suspend operator fun invoke(
        password: String
    ) {
        ds.putPassword(password = password)
    }
}