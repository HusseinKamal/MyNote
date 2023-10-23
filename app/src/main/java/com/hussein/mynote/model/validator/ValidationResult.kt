package com.hussein.mynote.model.validator

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String = ""
)
