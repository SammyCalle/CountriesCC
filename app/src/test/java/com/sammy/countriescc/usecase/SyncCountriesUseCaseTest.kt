package com.sammy.countriescc.usecase

import com.sammy.countriescc.domain.repository.CountriesRepository
import com.sammy.countriescc.domain.usecase.SyncCountriesUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SyncCountriesUseCaseTest {

    private val repository = mockk<CountriesRepository>()
    private val useCase = SyncCountriesUseCase(repository)

    @Test
    fun `invoke calls syncIfNeeded on repository`() = runTest {
        coEvery { repository.syncIfNeeded() } just Runs

        useCase()

        coVerify(exactly = 1) { repository.syncIfNeeded() }

    }

    @Test
    fun `invoke propagates exception from repository`() = runTest {
        coEvery { repository.syncIfNeeded() } throws Exception("Network error")

        val exception = assertFailsWith<Exception> { useCase() }
        assertEquals("Network error", exception.message)
    }
}