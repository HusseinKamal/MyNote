package com.hussein.mynote.model.validator

sealed class NoteFormEvent {
    data class TitleChanged(val title:String) : NoteFormEvent()
    data class DescriptionChanged(val description:String) : NoteFormEvent()
    data class TimeChanged(val time:String) : NoteFormEvent()
    object Submit:NoteFormEvent()

}