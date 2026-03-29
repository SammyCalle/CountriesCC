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

fun List<Country>.toEntity() : List<CountriesEntity> {
    return map { country -> country.toEntity() }
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
        coatOfArms = coatOfArms.png,
        flag = flags.png,
        flagDescription = flags.alt,
        currencyName = currencies.name,
        currencySymbol = currencies.symbol,
        capital = capital,
        population = population,
        languages = languages.map { it.value.name },
        continents = continents,
        timezones = timezones,
        googleMaps = maps.googleMaps

    )
}