package com.example.spotifymultithread.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spotifymultithread.model.Track

@Database(entities = [Track::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class SpotifyDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

}