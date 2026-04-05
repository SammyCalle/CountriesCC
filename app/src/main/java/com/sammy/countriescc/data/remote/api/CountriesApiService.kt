package com.sammy.countriescc.data.remote.api

import com.sammy.countriescc.data.remote.dto.detail.CountryDetailResponse
import com.sammy.countriescc.data.remote.dto.list.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {

    @GET("all?fields=name,cca3")
    suspend fun getListOfCountriesNames() : List<CountryDto>

    @GET("alpha/{code}?fields=name,capital,continents,maps,population,timezones,currencies,languages,flags,coatOfArms")
    suspend fun getCountryByCode(@Path("code") code: String) : CountryDetailResponse
}