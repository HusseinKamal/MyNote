package com.hussein.mynote.model.use_case

import android.content.Context
import com.hussein.mynote.R
import com.hussein.mynote.model.validator.ValidationResult

class ValidateTitle {
    fun execute(context: Context,title:String): ValidationResult {
        if(title.isEmpty()){
            return ValidationResult(successful = false,errorMessage = context.resources.getString(R.string.title_required))
        }
        return ValidationResult(successful = true)
    }
}