package com.sammy.countriescc.data.repository

import androidx.paging.PagingData
import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.remote.api.CountriesApiService
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesApi : CountriesApiService,
    private val countriesDao : CountriesDao
) : CountriesRepository {

    override suspend fun getListOfCountriesNames(): Flow<PagingData<CountrySummary>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCountryByCode(code: String): CountryDetail {
        TODO("Not yet implemented")
    }

}
