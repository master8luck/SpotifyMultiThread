package com.example.spotifymultithread.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spotifymultithread.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: SpotifyRepository,
): ViewModel() {
    fun fetchData(href: String) = repository.fetchDetail(href)
}