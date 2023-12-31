package com.hussein.mynote.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.hussein.mynote.screens.AddNoteScreen
import com.hussein.mynote.screens.HomeScreen
import com.hussein.mynote.util.Routes
import com.hussein.mynote.util.Routes.ADD_NOTE_SCREEN
import com.hussein.mynote.util.Routes.NOTES_ROUTE
import com.hussein.mynote.viewmodel.NoteViewModel

@ExperimentalPagingApi
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Routes.HOME_ROUTE,
        startDestination = NOTES_ROUTE
    ) {
        composable(route = NOTES_ROUTE) {
            HomeScreen(navController = navController)
        }
        composable(route = ADD_NOTE_SCREEN) {
           AddNoteScreen(navController = navController)
        }
    }
}