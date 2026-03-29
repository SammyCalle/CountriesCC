package com.sammy.countriescc.data.local.database

import androidx.room.Database
import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.local.entities.CountriesEntity

@Database(entities = [CountriesEntity::class], version = 1)
abstract class AppDatabase {

    abstract fun countriesDao(): CountriesDao

}