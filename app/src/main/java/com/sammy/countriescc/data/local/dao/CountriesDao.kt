package com.sammy.countriescc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sammy.countriescc.data.local.entities.CountriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT COUNT(*) FROM countries")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news : List<CountriesEntity>)

    @Query("SELECT * FROM countries ORDER BY name ASC")
    fun getAllCountries(): Flow<List<CountriesEntity>>

    @Query("SELECT * FROM countries WHERE name LIKE :query || '%' ORDER BY name ASC")
    fun searchCountries(query: String): Flow<List<CountriesEntity>>

}