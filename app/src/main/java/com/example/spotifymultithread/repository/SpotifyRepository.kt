package com.example.spotifymultithread.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.spotifymultithread.database.TrackDao
import com.example.spotifymultithread.di.ConnectionUtils.genAuthKey
import com.example.spotifymultithread.model.Track
import com.example.spotifymultithread.retrofit.AuthApi
import com.example.spotifymultithread.retrofit.SpotifyAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyRepository @Inject constructor(
    private val authApi: AuthApi,
    private val spotifyAPI: SpotifyAPI,
    private val trackDao: TrackDao,
) {

    private lateinit var token: String
    var query = ""
    val tracks : LiveData<List<Track>> by lazy { trackDao.getTracks() }

    init {
        authApi.getToken(bearer =  genAuthKey())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                token = "${it.type} ${it.token}"
            }, {
                Log.d("Token", ": ${it.message}")
            })

    }

    fun clearTracks() {
        Observable.fromCallable { trackDao.clearTracks() }
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {

            })
    }

    fun search(query: String, offset: Int) =
        spotifyAPI.getTracks(refreshToken = token, query = query, offset = offset)

    fun fetchDetail() {

    }

    fun insert(tracks: List<Track>) {
        trackDao.insert(tracks)
    }


}