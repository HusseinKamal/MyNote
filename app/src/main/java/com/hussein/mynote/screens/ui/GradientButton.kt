package com.hussein.mynote.screens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(text:String, textColor: Color, gradient: Brush, width: Dp = 100.dp, onClick:()->Unit){
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {
        Box(modifier = Modifier
            .background(gradient).width(width)
            .padding(all = 20.dp), contentAlignment = Alignment.Center){
            Text(text = text, color = textColor, fontSize =20.sp)
        }
    }

}