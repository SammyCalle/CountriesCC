package com.sammy.countriescc.data.remote.dto.list

data class Country(
    val name: NameDetails,
    val cca3 : String
)
data class NameDetails(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeNameDetails>
)

data class NativeNameDetails (
    val official : String,
    val common : String
)
