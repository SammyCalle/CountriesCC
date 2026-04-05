package com.sammy.countriescc.usecase

import com.sammy.countriescc.domain.repository.CountriesRepository
import com.sammy.countriescc.domain.usecase.GetCountriesUseCase
import io.mockk.Awaits
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCountriesUseCaseTest {

    private val repository = mockk<CountriesRepository>()
    private val useCase = GetCountriesUseCase(repository)

    @Test
    fun `invoke calls getListOfCountriesNames on repository`() = runTest {
        every { repository.getListOfCountriesNames(any()) } returns flowOf(emptyList())

        useCase("query")

        verify { repository.getListOfCountriesNames("query") }

    }

    @Test
        fun `invoke propagates exception from repository`() = runTest {
            every { repository.getListOfCountriesNames(any()) } throws Exception("DB error")

            val exception = assertFailsWith<Exception> { useCase("query") }
            assertEquals("DB error", exception.message)

        }

}