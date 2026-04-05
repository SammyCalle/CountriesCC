package com.sammy.countriescc.viewmodels

import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.domain.usecase.GetCountriesUseCase
import com.sammy.countriescc.domain.usecase.SyncCountriesUseCase
import com.sammy.countriescc.presentation.search.SearchScreenUiState
import com.sammy.countriescc.presentation.search.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import kotlin.collections.emptyList

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @MockK
    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @MockK
    private lateinit var syncCountriesUseCase: SyncCountriesUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { syncCountriesUseCase() } just Runs

    }

    private fun createViewModel() {
        viewModel = SearchViewModel(getCountriesUseCase, syncCountriesUseCase)

    }

    // region sync

    @Test
    fun `init triggers syncCountriesUseCase`() = runTest {
        every { getCountriesUseCase(any()) } returns flowOf(emptyList())

        createViewModel()
        advanceUntilIdle()

        coVerify(exactly = 1) { syncCountriesUseCase() }
    }

    @Test
    fun `init sync failure does not crash the ViewModel`() = runTest {
        every { getCountriesUseCase(any()) } returns flowOf(emptyList())
        coEvery { syncCountriesUseCase() } throws Exception("Network error")

        createViewModel()

        val job = launch { viewModel.uiState.collect {} }
        advanceTimeBy(350L)
        advanceUntilIdle()

        assert(viewModel.uiState.value is SearchScreenUiState.Success)

        job.cancel()
    }

    // endregion


    // region uiState
    @Test
    fun `initial uiState is Loading`() = runTest {
        every { getCountriesUseCase(any()) } returns flowOf(emptyList())
        createViewModel()

        assert(viewModel.uiState.value is SearchScreenUiState.Loading)
    }


    @Test
    fun `uiState emits Success with results for empty query`() = runTest {

        val countries = listOf(
            CountrySummary(
                "Germany",
                code = "GG3"
            ), CountrySummary(
                "Estonia",
                code = "EE2"
            )
        )

        every { getCountriesUseCase("") } returns flowOf(countries)

        createViewModel()

        val states = mutableListOf<SearchScreenUiState>()
        val job = launch { viewModel.uiState.collect { uiState ->
            states.add(uiState)
        } }

        advanceTimeBy(350L)
        advanceUntilIdle()

        assert(states.last() is SearchScreenUiState.Success)
        assertEquals(countries, (states.last() as SearchScreenUiState.Success).countries)

        job.cancel()

    }

    @Test
    fun `uiState emits Loading before results arrive`() = runTest {
        every { getCountriesUseCase("") } returns flow {
            delay(1000L)
            emit(emptyList())
        }

        createViewModel()

        val states = mutableListOf<SearchScreenUiState>()
        val job = launch { viewModel.uiState.collect { states.add(it) } }
        advanceTimeBy(350L)

        assert(states.any { it is SearchScreenUiState.Loading })
        job.cancel()
    }

    @Test
    fun `uiState emits Error when getCountriesUseCase throws`() = runTest {
        every { getCountriesUseCase(any()) } returns flow { throw Exception("DB error") }

        createViewModel()

        val states = mutableListOf<SearchScreenUiState>()
        val job = launch { viewModel.uiState.collect { states.add(it) } }

        advanceTimeBy(350L)
        advanceUntilIdle()

        assert(states.last() is SearchScreenUiState.Error)
        assertEquals("DB error", (states.last() as SearchScreenUiState.Error).message)

        job.cancel()

    }

    // endregion

    // region debounce

    @Test
    fun `rapid query changes trigger one use case call due to debounce`() = runTest {
        every { getCountriesUseCase(any()) } returns flowOf(emptyList())

        createViewModel()

        val job = launch { viewModel.uiState.collect {} }
        viewModel.onQueryChange("g")
        advanceTimeBy(100L)
        viewModel.onQueryChange("ge")
        advanceTimeBy(100L)
        viewModel.onQueryChange("ger")
        advanceTimeBy(350L)
        advanceUntilIdle()

        verify(exactly = 1) { getCountriesUseCase("ger") }
        verify(exactly = 0) { getCountriesUseCase("g") }
        verify(exactly = 0) { getCountriesUseCase("ge") }

        job.cancel()
    }

    // endregion

    // region onQueryChange

    @Test
    fun `onQueryChange updates query StateFlow`() = runTest {
        every { getCountriesUseCase(any()) } returns flowOf(emptyList())

        createViewModel()

        viewModel.onQueryChange("germany")

        assertEquals("germany", viewModel.query.value)
    }

    // endregion

}