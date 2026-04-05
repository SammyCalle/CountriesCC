package com.sammy.countriescc.mappers

import com.sammy.countriescc.data.mapper.toDomain
import com.sammy.countriescc.data.remote.dto.detail.CountryCoatOfArms
import com.sammy.countriescc.data.remote.dto.detail.CountryCurrency
import com.sammy.countriescc.data.remote.dto.detail.CountryDetailResponse
import com.sammy.countriescc.data.remote.dto.detail.CountryFlag
import com.sammy.countriescc.data.remote.dto.detail.CountryMap
import com.sammy.countriescc.data.remote.dto.list.NameDetails
import com.sammy.countriescc.data.remote.dto.list.NativeNameDetails
import org.junit.Assert
import org.junit.Test

class CountryDetailResponseTest {

    private val response = CountryDetailResponse(
        flags = CountryFlag(
            png = "https://flagcdn.com/w320/pe.png",
            svg = "https://flagcdn.com/pe.svg",
            alt = "Flag of Peru"
        ),
        coatOfArms = CountryCoatOfArms(
            png = "https://mainfacts.com/media/images/coats_of_arms/pe.png",
            svg = "https://mainfacts.com/media/images/coats_of_arms/pe.svg"
        ),
        name = NameDetails(
            common = "Peru",
            official = "Republic of Peru",
            nativeName = mapOf(
                "native1" to NativeNameDetails("common1", "official1")
            )
        ),
        currencies = mapOf(
            "currency1" to CountryCurrency("currency1", "symbol1"),
            "currency2" to CountryCurrency("currency2", "symbol2")
        ),
        languages = mapOf(
            "language1" to "nativeName1",
            "language2" to "nativeName2"
        ),
        capital = listOf("Lima"),
        maps = CountryMap(
            googleMaps = "https://goo.gl/maps/uDWEUaujHZJFkXoY6",
            openStreetMaps = "https://www.openstreetmap.org/relation/288247"
        ),
        population = 32971854,
        timezones = listOf("UTC-05:00"),
        continents = listOf("South America")

    )

    @Test
    fun `toDomain maps name correctly`() {
        val result = response.toDomain()
        Assert.assertEquals("Peru", result.name)
    }

    @Test
    fun `toDomain maps coatOfArms correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            "https://mainfacts.com/media/images/coats_of_arms/pe.png",
            result.coatOfArms
        )
    }

    @Test
    fun `toDomain maps flag correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            "https://flagcdn.com/w320/pe.png",
            result.flag
        )
    }

    @Test
    fun `toDomain maps flagDescription correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            "Flag of Peru", result.flagDescription
        )
    }

    @Test
    fun `toDomain maps currencyName correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            listOf("currency1", "currency2"), result.currencyName
        )
    }

    @Test
    fun `toDomain maps capital correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            listOf("Lima"), result.capital
        )
    }

    @Test
    fun `toDomain maps population correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            32971854, result.population
        )
    }

    @Test
    fun `toDomain maps languages correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            listOf("language1", "language2"), result.languages
        )
    }

    @Test
    fun `toDomain maps continents correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            listOf("South America"), result.continents
        )
    }

    @Test
    fun `toDomain maps timezones correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            listOf("UTC-05:00"), result.timezones
        )
    }

    @Test
    fun `toDomain maps googleMaps correctly`() {
        val result = response.toDomain()
        Assert.assertEquals(
            "https://goo.gl/maps/uDWEUaujHZJFkXoY6",
            result.googleMaps
        )
    }

}