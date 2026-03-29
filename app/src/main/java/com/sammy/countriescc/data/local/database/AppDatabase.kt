package com.sammy.countriescc.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.countriescc.data.local.dao.CountriesDao
import com.sammy.countriescc.data.local.entities.CountriesEntity

@Database(entities = [CountriesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao

}