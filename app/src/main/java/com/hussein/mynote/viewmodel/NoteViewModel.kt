package com.hussein.mynote.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.mynote.data.repository.RepositoryNote
import com.hussein.mynote.model.Note
import com.hussein.mynote.model.ResourceState
import com.hussein.mynote.model.use_case.ValidateDescription
import com.hussein.mynote.model.use_case.ValidateTime
import com.hussein.mynote.model.use_case.ValidateTitle
import com.hussein.mynote.model.validator.NoteFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor
    (
    private val noteRepo: RepositoryNote,
    private val  context: Application,
    )
    : ViewModel() {

    var validateTitle: ValidateTitle = ValidateTitle()
    var validateDescription: ValidateDescription = ValidateDescription()
    var validateTime: ValidateTime = ValidateTime()
   // var context:Application = Application()
    private val _notes : MutableStateFlow<ResourceState<List<Note>>> = MutableStateFlow(ResourceState.Loading())
    val notes: StateFlow<ResourceState<List<Note>>> get() = _notes

    //For validate model
    var state by mutableStateOf(Note())
    private val validateEventChannel = Channel<ValidationEvent>()
    val validateEvents = validateEventChannel.receiveAsFlow()

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
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepo.deleteNote(note)
            getNotes()
        }
    }

    //Validate Form fields
    fun onEvent(event:NoteFormEvent){
        when(event){
            is NoteFormEvent.TitleChanged ->{
                 state = state.copy(
                    title = event.title
                )
            }
            is NoteFormEvent.DescriptionChanged ->{
                state = state.copy(
                    description = event.description
                )
            }
            is NoteFormEvent.TimeChanged ->{
                state = state.copy(
                    time = event.time
                )
            }
            is NoteFormEvent.Submit ->{
                submitData()
            }
        }

    }
    private fun submitData(){
        val titleResult = validateTitle.execute(context = context, title = state.title)
        val descriptionResult = validateDescription.execute(context = context, description = state.description)
        val timeResult = validateTime.execute(context = context, time = state.time)
        val hasError = listOf(
            titleResult,
            descriptionResult,
            timeResult
        ).any { !it.successful}
        if(hasError){
            state = state.copy(
                titleError = titleResult.errorMessage,
                descriptionError = descriptionResult.errorMessage,
                timeError = timeResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validateEventChannel.send(ValidationEvent.Success(state))
        }
    }
    sealed class ValidationEvent{
        data class Success(val state:Note): ValidationEvent()
        //object Success: ValidationEvent()
    }

}