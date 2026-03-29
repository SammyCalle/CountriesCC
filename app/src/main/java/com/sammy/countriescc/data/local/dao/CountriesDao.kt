package com.sammy.countriescc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammy.countriescc.data.local.entities.CountriesEntity

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news : List<CountriesEntity>)

    @Query("DELETE FROM countries")
    suspend fun deleteAll()

}