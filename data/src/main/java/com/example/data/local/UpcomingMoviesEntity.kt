package com.example.data.local

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.response.Result

@Entity(tableName = "upcomingMovies")
data class UpcomingMoviesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "poster") val posterPath: String? = "",
    @ColumnInfo(name = "backdrop") val backdropPath: String? = "",
    @ColumnInfo(name = "voteAverage") val voteAverage: Double? = 0.0,
    @ColumnInfo(name = "releaseDate") val releaseDate: String? = "",
    @ColumnInfo(name = "description") val overview: String? = ""

){
    companion object {
        fun Result.toMoviesDataEntity(): UpcomingMoviesEntity {
            return UpcomingMoviesEntity(
                id = id,
                title = title,
                posterPath = poster_path,
                backdropPath = backdrop_path,
                voteAverage = vote_average,
                releaseDate = release_date,
                overview = overview
            )
        }
    }
}

@Database(entities = [UpcomingMoviesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
