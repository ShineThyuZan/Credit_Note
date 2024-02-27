package com.omgea.mynote.use_cases

import com.omgea.mynote.repository.PasswordDsRepository
import javax.inject.Inject

class LocalePutUseCase @Inject constructor(
    private val ds: PasswordDsRepository
) {
    suspend operator fun invoke(
        isDefaultLocale: Boolean
    ) {
        ds.putLocale(isDefaultLocale)
    }
}