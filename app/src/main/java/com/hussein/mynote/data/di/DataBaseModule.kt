package com.hussein.mynote.data.di

import android.content.Context
import androidx.room.Room
import com.hussein.mynote.data.local.NoteDatabase
import com.hussein.mynote.data.local.dao.NoteDao
import com.hussein.mynote.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : NoteDatabase {
        return NoteDatabase.getDatabase(context)
        //return Room.databaseBuilder(context, NoteDatabase::class.java, Constant.NOTE_DATABASE).build()

    }

    @Singleton
    @Provides
    fun provideUserDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

}