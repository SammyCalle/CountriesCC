package com.sammy.countriescc.presentation.search

import com.sammy.countriescc.domain.model.CountrySummary

sealed class SearchScreenUiState {
    object Loading : SearchScreenUiState()
    data class Success(val countries: List<CountrySummary>) : SearchScreenUiState()
    data class Error(val message: String) : SearchScreenUiState()
}