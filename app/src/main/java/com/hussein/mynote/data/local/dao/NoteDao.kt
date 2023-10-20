package com.hussein.mynote.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hussein.mynote.data.di.DataBaseModule
import com.hussein.mynote.model.Note
import dagger.Component
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes():List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note:Note)

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNote(id : Int):Note

    @Query("DELETE FROM note_table WHERE id = :id")
    fun deleteNote(id : Int)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

}