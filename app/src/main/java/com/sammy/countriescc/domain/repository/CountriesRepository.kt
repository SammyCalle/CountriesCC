package com.sammy.countriescc.domain.repository

import androidx.paging.PagingData
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.model.CountrySummary
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    suspend fun getListOfCountriesNames() : Flow<PagingData<CountrySummary>>
    suspend fun getCountryByCode(code: String) : CountryDetail
}