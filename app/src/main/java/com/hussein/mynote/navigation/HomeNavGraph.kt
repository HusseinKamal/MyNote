package com.hussein.mynote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.hussein.mynote.screens.AddNoteScreen
import com.hussein.mynote.screens.HomeScreen
import com.hussein.mynote.util.Routes
import com.hussein.mynote.util.Routes.ADD_NOTE_SCREEN
import com.hussein.mynote.util.Routes.NOTES_ROUTE

@ExperimentalPagingApi
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Routes.HOME_ROUTE,
        startDestination = ADD_NOTE_SCREEN
    ) {
        composable(route = NOTES_ROUTE) {
            HomeScreen(navController = navController)
        }
        composable(route = ADD_NOTE_SCREEN) {
           AddNoteScreen(navController = navController)
        }
    }
}