package com.example.spotifymultithread.di

import android.content.Context
import androidx.room.Room
import com.example.spotifymultithread.database.SpotifyDatabase
import com.example.spotifymultithread.database.TrackDao
import com.example.spotifymultithread.di.ConnectionUtils.API_AUTH_ENDPOINT
import com.example.spotifymultithread.di.ConnectionUtils.API_ENDPOINT
import com.example.spotifymultithread.retrofit.AuthApi
import com.example.spotifymultithread.retrofit.SpotifyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    @Named("request")
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_AUTH_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthApi(@Named("auth") retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideSpotifyApi(@Named("request") retrofit: Retrofit): SpotifyAPI =
        retrofit.create(SpotifyAPI::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpotifyDatabase =
        Room.databaseBuilder(context, SpotifyDatabase::class.java, "spotify.db").build()

    @Provides
    @Singleton
    fun provideTrackDao(database: SpotifyDatabase) : TrackDao = database.trackDao()

}