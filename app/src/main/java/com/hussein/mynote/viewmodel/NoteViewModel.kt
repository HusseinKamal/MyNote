package com.hussein.mynote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.mynote.data.repository.RepositoryNote
import com.hussein.mynote.model.Note
import com.hussein.mynote.model.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor
    (private val noteRepo: RepositoryNote)
    : ViewModel() {

    private val _notes : MutableStateFlow<ResourceState<List<Note>>> = MutableStateFlow(ResourceState.Loading())
    val notes: StateFlow<ResourceState<List<Note>>> get() = _notes

    //val noteList: Flow<List<Note>> = noteRepo.allNotes

    init {
        getNotes()
    }
    private fun getNotes() {
        viewModelScope.launch {
            try {
                noteRepo.getAllNotes().collect{ notes ->
                    _notes.value = notes
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepo.addNote(note)
        }
    }

}