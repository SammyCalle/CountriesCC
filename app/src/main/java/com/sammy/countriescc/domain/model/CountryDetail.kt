package com.sammy.countriescc.domain.model

data class CountryDetail(
    val name : String,
    val coatOfArms : String,
    val flag : String,
    val flagDescription : String,
    val currencyName : List<String>,
    val capital : List<String>,
    val population : Int,
    val languages : List<String>,
    val continents : List<String>,
    val timezones : List<String>,
    val googleMaps : String
)
