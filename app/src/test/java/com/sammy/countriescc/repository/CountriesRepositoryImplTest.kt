package com.sammy.countriescc.repository

import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.local.entities.CountriesEntity
import com.sammy.countriescc.data.mapper.toEntity
import com.sammy.countriescc.data.remote.api.CountriesApiService
import com.sammy.countriescc.data.remote.dto.list.CountryDto
import com.sammy.countriescc.data.remote.dto.list.NameDetails
import com.sammy.countriescc.data.remote.dto.list.NativeNameDetails
import com.sammy.countriescc.data.repository.CountriesRepositoryImpl
import com.sammy.countriescc.domain.model.CountrySummary
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CountriesRepositoryImplTest {

    private val countriesApi = mockk<CountriesApiService>()
    private val countriesDao = mockk<CountriesDao>()

    private val repository = CountriesRepositoryImpl(countriesApi, countriesDao)

    @Test
    fun `syncIfNeeded fetches and inserts when DB is empty`() = runTest {
        val apiResponse = listOf(
            CountryDto(
                name = NameDetails(
                    common = "commonTest1",
                    official = "officialTest1",
                    nativeName = mapOf(
                        "nativeTest1" to
                                NativeNameDetails("officialTest1", "commonTest1")
                    )
                ),
                cca3 = "cct"
            )
        )

        coEvery { countriesDao.count() } returns 0
        coEvery { countriesApi.getListOfCountriesNames() } returns apiResponse
        coEvery { countriesDao.insertAll(any()) } just Runs

        repository.syncIfNeeded()
        coVerify(exactly = 1) { countriesApi.getListOfCountriesNames() }
        coVerify(exactly = 1) { countriesDao.insertAll(any()) }
    }

    @Test
    fun `syncIfNeeded does not fetch or insert when DB is not empty` () = runTest {
        coEvery { countriesDao.count() } returns 1

        repository.syncIfNeeded()
        coVerify(exactly = 0) { countriesApi.getListOfCountriesNames() }
        coVerify(exactly = 0) { countriesDao.insertAll(any()) }

    }

    @Test
    fun `syncIfNeeded maps api response to entities correctly`() = runTest {
        val apiResponse = listOf(
            CountryDto(
                name = NameDetails(
                    common = "commonTest1",
                    official = "officialTest1",
                    nativeName = mapOf(
                        "nativeTest1" to
                                NativeNameDetails("officialTest1", "commonTest1")
                    )
                ),
                cca3 = "cct"
            )
        )
        val expectedEntities = apiResponse.map { it.toEntity() }

        coEvery { countriesDao.count() } returns 0
        coEvery { countriesApi.getListOfCountriesNames() } returns apiResponse
        coEvery { countriesDao.insertAll(any()) } just Runs

        repository.syncIfNeeded()

        coVerify { countriesDao.insertAll(expectedEntities) }
    }

    @Test
    fun `syncIfNeeded propagates API exception`() = runTest {
        coEvery { countriesDao.count() } returns 0
        coEvery { countriesApi.getListOfCountriesNames() } throws Exception("Network error")

        val exception = assertFailsWith<Exception> { repository.syncIfNeeded()}
        assertEquals("Network error", exception.message)

        coVerify(exactly = 0) { countriesDao.insertAll(any()) }
    }

    @Test
    fun `getListOfCountriesNames calls getAllCountries when query is blank`() = runTest {
        val entities = listOf(CountriesEntity(name = "Germany", countryCode = "DEU"))
        every { countriesDao.getAllCountries() } returns flowOf(entities)

        repository.getListOfCountriesNames("").first()

        verify(exactly = 1) { countriesDao.getAllCountries() }
        verify(exactly = 0) { countriesDao.searchCountries(any()) }
    }

    @Test
    fun `getListOfCountriesNames calls searchCountries when query is not blank`() = runTest {
        val entities = listOf(CountriesEntity(name = "Germany", countryCode = "DEU"))
        every { countriesDao.searchCountries("ger") } returns flowOf(entities)

        repository.getListOfCountriesNames("ger").first()

        verify(exactly = 1) { countriesDao.searchCountries("ger") }
        verify(exactly = 0) { countriesDao.getAllCountries() }
    }

    @Test
    fun `getListOfCountriesNames maps entities to domain models correctly`() = runTest {
        val entities = listOf(CountriesEntity(name = "Germany", countryCode = "DEU"))
        val expected = listOf(CountrySummary(name = "Germany", code = "DEU"))
        every { countriesDao.getAllCountries() } returns flowOf(entities)

        val result = repository.getListOfCountriesNames("").first()

        assertEquals(expected, result)
    }


}