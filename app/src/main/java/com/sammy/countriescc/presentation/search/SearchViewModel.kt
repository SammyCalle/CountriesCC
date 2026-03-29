package com.sammy.countriescc.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.domain.usecase.GetCountriesUseCase
import com.sammy.countriescc.domain.usecase.SyncCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val syncCountriesUseCase: SyncCountriesUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchScreenUiState> = _query
        .debounce(300L)
        .flatMapLatest { query ->
            getCountriesUseCase(query)
                .map<List<CountrySummary>, SearchScreenUiState> { SearchScreenUiState.Success(it) }
                .onStart { emit(SearchScreenUiState.Loading) }
                .catch { e -> emit(SearchScreenUiState.Error(e.message ?: "Unexpected error")) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchScreenUiState.Loading
        )

    init {
        viewModelScope.launch {
            try {
                syncCountriesUseCase()
            } catch (e: Exception) {
                SearchScreenUiState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}