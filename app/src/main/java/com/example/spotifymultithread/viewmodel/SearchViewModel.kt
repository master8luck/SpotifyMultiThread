package com.example.spotifymultithread.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spotifymultithread.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SpotifyRepository,
): ViewModel() {

    var countDownTimer: Disposable? = null
    private val availableIds = mutableListOf(0, 1, 2)
    private var queriesDone = 0
    private var lastOffset = 0

    val tracks = repository.tracks

    fun search(query: String) {
        repository.query = query
        queriesDone = 0
        lastOffset = 0
        repository.clearTracks()

        if (countDownTimer != null) {
            countDownTimer!!.dispose()
        }
        countDownTimer = Observable.interval(50, TimeUnit.MILLISECONDS)
            .subscribe {
                runThread(query)
                if (queriesDone > 9)
                    countDownTimer!!.dispose()
            }


    }

    private fun runThread(query: String) {
        if (availableIds.size > 0) {
            val id = availableIds[0]
            availableIds.removeAt(0)
            repository.search(query, lastOffset)
                .subscribeOn(Schedulers.io())
                .subscribe({ tracks ->
                    availableIds.add(0, id)
                    if (getQuery(tracks.tracksResponse.href) == query) {
                        Thread.sleep((50..350).random().toLong())
                        repository.insert(tracks.tracksResponse.items.onEach { it.threadId = id })
                        queriesDone++
                        lastOffset += 10
                    }
                }, {
                    availableIds.add(0, id)
                })
        }
    }


    private fun getQuery(href: String) =
        href.replaceBefore("?", "")
            .replaceAfter("&", "")
            .replaceBefore("=", "")
            .replace("=", "")
            .replace("&", "")

}