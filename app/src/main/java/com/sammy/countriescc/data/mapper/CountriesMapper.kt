package com.sammy.countriescc.data.mapper

import com.sammy.countriescc.data.local.entities.CountriesEntity
import com.sammy.countriescc.data.remote.dto.detail.CountryDetailResponse
import com.sammy.countriescc.data.remote.dto.list.Country
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.model.CountrySummary
import kotlin.collections.map

fun Country.toEntity() : CountriesEntity {
    return CountriesEntity(
        name = name.common,
        countryCode = cca3
    )
}

fun List<CountriesEntity>.toDomain() : List<CountrySummary> {
    return map {country -> country.toDomain() }
}

fun CountriesEntity.toDomain() : CountrySummary {
    return CountrySummary(
        name = name,
        code = countryCode
    )
}

fun CountryDetailResponse.toDomain() : CountryDetail {
    return CountryDetail(
        name = name.common,
        flag = flags.png,
        flagDescription = flags.alt,
        coatOfArms = coatOfArms.png,
        currencyName = currencies.values.map { it.name },
        capital = capital,
        population = population,
        languages = languages.values.toList(),  // values are already Strings
        continents = continents,
        timezones = timezones,
        googleMaps = maps.googleMaps

    )
}