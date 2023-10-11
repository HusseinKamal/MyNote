package com.hussein.mynote.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hussein.mynote.data.local.dao.NoteDao
import com.hussein.mynote.model.Note
import com.hussein.mynote.util.Constant
import dagger.Binds

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    Constant.NOTE_DATABASE
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }

}