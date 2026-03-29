package com.sammy.countriescc.di

import android.content.Context
import androidx.room.Room
import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.local.database.AppDatabase
import com.sammy.countriescc.data.remote.api.CountriesApiService
import com.sammy.countriescc.data.remote.api.RetrofitConfiguration
import com.sammy.countriescc.data.repository.CountriesRepositoryImpl
import com.sammy.countriescc.domain.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitConfiguration.countriesApi
    }

    @Provides
    @Singleton
    fun provideCountriesApiService(retrofit: Retrofit): CountriesApiService {
        return retrofit.create(CountriesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(
        countriesApi: CountriesApiService,
        countriesDao: CountriesDao
    ): CountriesRepository {
        return CountriesRepositoryImpl(countriesApi, countriesDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCountriesDao(
        database: AppDatabase
    ): CountriesDao {
        return database.countriesDao()
    }

}