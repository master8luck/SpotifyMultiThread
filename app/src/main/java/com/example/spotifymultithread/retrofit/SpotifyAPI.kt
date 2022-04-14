package com.example.spotifymultithread.retrofit

import com.example.spotifymultithread.model.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyAPI {

    @GET("search")
    fun getTracks(
        @Header("Authorization") refreshToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Single<SearchResponse>

}