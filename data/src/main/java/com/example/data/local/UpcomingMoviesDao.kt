package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpcomingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(clientsDataEntity: List<UpcomingMoviesEntity>)

    @Query("SELECT * FROM upcomingMovies")
    fun getMovies(): List<UpcomingMoviesEntity>
}