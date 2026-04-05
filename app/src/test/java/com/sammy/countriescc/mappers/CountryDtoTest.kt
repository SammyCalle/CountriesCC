package com.sammy.countriescc.mappers

import com.sammy.countriescc.data.mapper.toEntity
import com.sammy.countriescc.data.remote.dto.list.CountryDto
import com.sammy.countriescc.data.remote.dto.list.NameDetails
import com.sammy.countriescc.data.remote.dto.list.NativeNameDetails
import org.junit.Test
import kotlin.test.assertEquals

class CountryDtoTest {

    private val response = CountryDto(
        name = NameDetails(
            common = "Peru",
            official = "Republic of Peru",
            nativeName = mapOf("Native1" to NativeNameDetails(
                official = "Republic of Peru",
                common = "Peru"
            ))
        ),
        cca3 = "PP3"
    )

    @Test
    fun `toEntity maps name correctly`() {
        val result = response.toEntity()
        assertEquals("Peru", result.name)
    }

    @Test
    fun `toEntity maps countryCode correctly`() {
        val result = response.toEntity()
        assertEquals("PP3", result.countryCode)

    }
}