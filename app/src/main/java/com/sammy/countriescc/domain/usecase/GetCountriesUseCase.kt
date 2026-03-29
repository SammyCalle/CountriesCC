package com.sammy.countriescc.domain.usecase

import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountriesRepository
) {
    operator fun invoke(query: String): Flow<List<CountrySummary>> =
        repository.getListOfCountriesNames(query)
}