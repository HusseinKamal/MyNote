package com.hussein.mynote.screens.ui

import android.icu.text.CaseMap.Title
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.hussein.mynote.util.Routes

@Composable
fun CustomTopAppBar(title: String="Home",navController:NavHostController,routePopup:String,hasBackArrow: Boolean){
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        navigationIcon = {
            if(hasBackArrow) {
                IconButton(onClick = {
                    navController.navigate(routePopup) {
                        popUpTo(routePopup) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }
    )
}