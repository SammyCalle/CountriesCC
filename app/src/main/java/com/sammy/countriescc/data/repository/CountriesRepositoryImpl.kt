package com.sammy.countriescc.data.repository

import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.mapper.toDomain
import com.sammy.countriescc.data.mapper.toEntity
import com.sammy.countriescc.data.remote.api.CountriesApiService
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesApi: CountriesApiService,
    private val countriesDao: CountriesDao
) : CountriesRepository {

    override suspend fun syncIfNeeded() {
        if (countriesDao.count() == 0) {
            val entities = countriesApi.getListOfCountriesNames().map { country ->
                country.toEntity()
            }
            countriesDao.insertAll(entities)
        }
    }

    override fun getListOfCountriesNames(query: String): Flow<List<CountrySummary>> {
        val flow = if (query.isBlank()) countriesDao.getAllCountries()
        else countriesDao.searchCountries(query)
        return flow.map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getCountryByCode(code: String): CountryDetail {
        countriesApi.getCountryByCode(code).also { response ->
            return response.toDomain()
        }
    }

}
