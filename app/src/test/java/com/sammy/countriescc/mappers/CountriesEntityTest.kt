package com.sammy.countriescc.mappers

import com.sammy.countriescc.data.local.entities.CountriesEntity
import com.sammy.countriescc.data.mapper.toDomain
import org.junit.Test
import kotlin.test.assertEquals

class CountriesEntityTest {
    private val response = CountriesEntity(
        id = 1,
        name = "Peru",
        countryCode = "PP3"
    )

    @Test
    fun `toDomain maps name correctly`() {
        val result = response.toDomain()
        assertEquals("Peru", result.name)
    }

    @Test
    fun `toDomain maps countryCode correctly`() {
        val result = response.toDomain()
        assertEquals("PP3", result.code)
    }
}