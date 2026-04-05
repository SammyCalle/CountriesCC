package com.sammy.countriescc.usecase

import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.repository.CountriesRepository
import com.sammy.countriescc.domain.usecase.GetCountryDetailUseCase
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCountryDetailUseCaseTest {

    private val repository = mockk<CountriesRepository>()

    private val useCase = GetCountryDetailUseCase(repository)

    private val sampleCountry = CountryDetail(
        name = "Peru",
        coatOfArms = "https://mainfacts.com/media/images/coats_of_arms/pe.png",
        flag = "https://flagcdn.com/w320/pe.png",
        flagDescription = "Flag of Peru",
        currencyName = listOf("Peruvian sol"),
        capital = listOf("Lima"),
        population = 32971854,
        languages = listOf("Spanish"),
        continents = listOf("South America"),
        timezones = listOf("UTC-05:00"),
        googleMaps = "https://goo.gl/maps/uDWEUaujHZJFkXoY6"
    )


    @Test
    fun `invoke calls getCountryByCode on repository`() = runTest {
        coEvery { repository.getCountryByCode("Peru") } returns sampleCountry
        useCase("Peru")

        coVerify(exactly = 1) {
            repository.getCountryByCode("Peru")
        }
    }

    @Test
    fun `invoke propagates exception from repository`() = runTest {
        coEvery { repository.getCountryByCode(any()) } throws Exception("Network error")

        val exception = assertFailsWith<Exception> { useCase("code") }
        assertEquals("Network error", exception.message)
    }

}