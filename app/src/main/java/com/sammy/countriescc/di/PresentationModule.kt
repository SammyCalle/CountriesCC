package com.sammy.countriescc.di

import com.sammy.countriescc.domain.usecase.GetCountriesUseCase
import com.sammy.countriescc.domain.usecase.SyncCountriesUseCase
import com.sammy.countriescc.presentation.search.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    fun provideSearchViewModel(
        getCountriesUseCase: GetCountriesUseCase,
        syncCountriesUseCase: SyncCountriesUseCase
    ): SearchViewModel {
        return SearchViewModel(getCountriesUseCase, syncCountriesUseCase)

    }
}