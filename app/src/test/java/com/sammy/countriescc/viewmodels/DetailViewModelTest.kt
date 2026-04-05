package com.sammy.countriescc.viewmodels

import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.domain.usecase.GetCountryDetailUseCase
import com.sammy.countriescc.presentation.detail.DetailScreenUiState
import com.sammy.countriescc.presentation.detail.DetailViewModel
import io.mockk.Awaits
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var getCountryDetailUseCase: GetCountryDetailUseCase

    private lateinit var viewModel: DetailViewModel

    val countryDetail = CountryDetail(
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { getCountryDetailUseCase(any()) } just Awaits

    }

    private fun createViewModel() {
        viewModel = DetailViewModel(getCountryDetailUseCase)

    }

    @Test
    fun `initial uiState is Loading`() = runTest {

        createViewModel()
        viewModel.loadCountry("PP3")

        assert(viewModel.uiState.value is DetailScreenUiState.Loading)

    }

    @Test
    fun `uiState emits Success after result arrives`() = runTest{


        coEvery { getCountryDetailUseCase("PP3") } returns countryDetail

        createViewModel()

        viewModel.loadCountry("PP3")

        assert(viewModel.uiState.value is DetailScreenUiState.Success)
        assert((viewModel.uiState.value as DetailScreenUiState.Success).country == countryDetail)
    }

    @Test
    fun `uiState emits Error when getCountryDetailUseCase throws`() = runTest {
        coEvery { getCountryDetailUseCase(any()) } throws Exception("DB error")

        createViewModel()

        viewModel.loadCountry("PP3")

        assert(viewModel.uiState.value is DetailScreenUiState.Error)
        assert((viewModel.uiState.value as DetailScreenUiState.Error).message == "DB error")

    }

    @Test
    fun `calling loadCountry again resets state to Loading`() = runTest {
        coEvery { getCountryDetailUseCase("PP3") } returns countryDetail

        createViewModel()
        viewModel.loadCountry("PP3")

        coEvery { getCountryDetailUseCase("EE") } just Awaits
        viewModel.loadCountry("EE")

        assert(viewModel.uiState.value is DetailScreenUiState.Loading)
    }



}