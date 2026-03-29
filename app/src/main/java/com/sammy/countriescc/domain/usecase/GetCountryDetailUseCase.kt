package com.sammy.countriescc.domain.usecase

import com.sammy.countriescc.domain.repository.CountriesRepository
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    suspend operator fun invoke(countryCode: String) =
        repository.getCountryByCode(countryCode)

}