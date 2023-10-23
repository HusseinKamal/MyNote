package com.hussein.mynote.util

object Constant {
    const val NOTE_TABLE ="note_table"
    const val NOTE_DATABASE ="note_database"

    fun addZeroBefore(text: String):String{
        if(text.length == 1){
            return "0$text"
        }
        return text
    }
}