package com.example.spotifymultithread.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.spotifymultithread.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SpotifyRepository,
) : ViewModel() {

    private var lastOffset = 0
    private var stopThreads: Boolean = false

    val tracks = repository.tracks

    fun search(query: String) {
        stopThreads = false
        repository.query = query
        lastOffset = 0
        repository.clearTracks()

        for (i in 0..1) {
            runThread(query, i)
        }

    }

    private fun runThread(query: String, threadId: Int) {
        Log.d("qwe", "runThread: $threadId ${Thread.currentThread().id}")
        lastOffset += 10
        repository.search(query, lastOffset)
            .subscribeOn(Schedulers.io())
            .subscribe({ tracks ->
                if (getQuery(tracks.tracksResponse.href) == query && !stopThreads) {
                    repository.insert(tracks.tracksResponse.items.onEach { it.threadId = threadId })

                    if (lastOffset < 100 && lastOffset < tracks.tracksResponse.total)
                        runThread(query, threadId)

                }
            }, {

            })
    }


    private fun getQuery(href: String) =
        href.replaceBefore("?", "")
            .replaceAfter("&", "")
            .replaceBefore("=", "")
            .replace("=", "")
            .replace("&", "")

    fun stopThreads() {
        stopThreads = true
    }

}