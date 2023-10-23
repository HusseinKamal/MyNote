package com.hussein.mynote.model.use_case

import android.content.Context
import com.hussein.mynote.R
import com.hussein.mynote.model.validator.ValidationResult

class ValidateTime {
    fun execute(context: Context, time:String): ValidationResult {
        if(time.isEmpty()){
            return ValidationResult(successful = false,errorMessage = context.resources.getString(R.string.time_required))
        }
        return ValidationResult(successful = true)
    }
}