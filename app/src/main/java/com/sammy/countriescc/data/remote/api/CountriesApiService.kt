package com.sammy.countriescc.data.remote.api

import com.sammy.countriescc.data.remote.dto.list.CountryList
import com.sammy.countriescc.domain.model.CountryDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {

    @GET("all?fields=name,cca3")
    suspend fun getListOfCountriesNames() : CountryList

    @GET("alpha/{code}?fields=name,capital,continents,maps,population,timezones,currencies,languages,flags,coatOfArms")
    suspend fun getCountryByCode(@Path("code") code: String) : CountryDetail
}