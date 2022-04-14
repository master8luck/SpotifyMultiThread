package com.example.spotifymultithread.database

import androidx.room.TypeConverter
import com.example.spotifymultithread.model.Album
import com.example.spotifymultithread.model.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromAlbum(album: Album?): String? {
        if (album == null) return null
        val type = object : TypeToken<List<Image?>?>() {}.type
        return Gson().toJson(album.images, type)
    }

    @TypeConverter
    fun toAlbum(album: String?): Album? {
        if (album == null) return null
        val type = object : TypeToken<List<Image?>?>() {}.type
        return Album(Gson().fromJson(album, type))
    }

}