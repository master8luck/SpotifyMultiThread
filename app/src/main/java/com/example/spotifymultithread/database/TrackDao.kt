package com.example.spotifymultithread.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spotifymultithread.model.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM track")
    fun getTracks() : LiveData<List<Track>>

    @Query("DELETE FROM track")
    fun clearTracks()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tracks: List<Track>)


}