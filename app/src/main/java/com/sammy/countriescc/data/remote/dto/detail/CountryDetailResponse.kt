package com.sammy.countriescc.data.remote.dto.detail

import com.sammy.countriescc.data.remote.dto.list.NameDetails


data class CountryDetailResponse(
    val flags: CountryFlag,
    val coatOfArms: CountryCoatOfArms,
    val name: NameDetails,
    val currencies: Map<String, CountryCurrency>,
    val languages: Map<String, String>,
    val capital: List<String>,
    val maps: CountryMap,
    val population: Int,
    val timezones: List<String>,
    val continents: List<String>
)

data class CountryCurrency(
    val name: String,
    val symbol: String
)

data class CountryMap(
    val googleMaps: String,
    val openStreetMaps: String
)

class CountryFlag(
    val png: String,
    val svg: String,
    val alt: String
)

class CountryCoatOfArms(
    val png: String,
    val svg: String
)
