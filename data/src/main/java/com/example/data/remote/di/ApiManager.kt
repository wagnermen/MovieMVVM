package com.example.data.remote.di

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.UpcomingMoviesDao
import com.example.data.remote.api.ApiMovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org"
const val apiKey = "d9ae4921794c06bd0fdbd1463d274804"
const val language = "en-US"

@Module
@InstallIn(SingletonComponent::class)
object ApiManager {

    @Singleton
    @Provides
    fun provideMovieApi(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey, language))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): ApiMovieService {
        return retrofit.create(ApiMovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideClientsDao(database: AppDatabase): UpcomingMoviesDao {
        return database.upcomingMoviesDao()
    }

}