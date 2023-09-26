package com.hussein.mynote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hussein.mynote.data.local.dao.NoteDao
import com.hussein.mynote.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {
    abstract fun noteDao(): NoteDao

}