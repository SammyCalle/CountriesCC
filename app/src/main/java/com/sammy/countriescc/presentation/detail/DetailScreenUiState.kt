package com.sammy.countriescc.presentation.detail

import com.sammy.countriescc.domain.model.CountryDetail

sealed class DetailScreenUiState {
    object Loading : DetailScreenUiState()
    data class Success(val country: CountryDetail) : DetailScreenUiState()
    data class Error(val message: String) : DetailScreenUiState()
}