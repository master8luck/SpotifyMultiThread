package com.example.spotifymultithread.retrofit

import com.example.spotifymultithread.model.AuthResponce
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("token")
    fun getToken(
        @Query("grant_type") grantType: String = "client_credentials",
        @Header("Authorization") bearer: String,
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
    ): Single<AuthResponce>

}