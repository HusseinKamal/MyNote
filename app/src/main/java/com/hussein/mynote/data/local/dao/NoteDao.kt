package com.hussein.mynote.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hussein.mynote.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes():List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note:Note)

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNote(id : Int):Note

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

}