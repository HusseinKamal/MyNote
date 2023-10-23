package com.hussein.mynote.model.use_case

import android.content.Context
import com.hussein.mynote.R
import com.hussein.mynote.model.validator.ValidationResult

class ValidateDescription {
    fun execute(context: Context, description:String): ValidationResult {
        if(description.isEmpty()){
            return ValidationResult(successful = false,errorMessage = context.resources.getString(R.string.description_required))
        }
        return ValidationResult(successful = true)
    }
}