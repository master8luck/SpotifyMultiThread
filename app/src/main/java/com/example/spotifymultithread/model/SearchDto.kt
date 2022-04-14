package com.example.spotifymultithread.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("tracks")
    val tracksResponse: TracksResponse,
)

data class Album(
    @SerializedName("images")
    val images: List<Image>,
)

data class Image(
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int,
    @SerializedName("url")
    val url: String,
)


data class TracksResponse(
    @SerializedName("items")
    val items: List<Track>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("href")
    val href: String,
)

@Entity
data class Track(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("href")
    val href: String,
    @SerializedName("album")
    val album: Album,
    var threadId: Int = 0,
)