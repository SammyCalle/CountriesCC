package com.sammy.countriescc.di

import com.sammy.countriescc.domain.repository.CountriesRepository
import com.sammy.countriescc.domain.usecase.GetCountriesUseCase
import com.sammy.countriescc.domain.usecase.SyncCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(repository: CountriesRepository): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSyncCountriesUseCase(repository: CountriesRepository): SyncCountriesUseCase {
        return SyncCountriesUseCase(repository)
    }

}