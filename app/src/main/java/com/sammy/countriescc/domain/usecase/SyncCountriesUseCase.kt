package com.sammy.countriescc.domain.usecase

import com.sammy.countriescc.domain.repository.CountriesRepository
import javax.inject.Inject

class SyncCountriesUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    suspend operator fun invoke() = repository.syncIfNeeded()
}