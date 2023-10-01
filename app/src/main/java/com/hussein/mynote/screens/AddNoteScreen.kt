package com.hussein.mynote.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.hussein.mynote.R

@ExperimentalPagingApi
@Composable
fun AddNoteScreen(navController: NavHostController/*,noteViewModel: NoteViewModel = hiltViewModel()*/) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Home",
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
            )
        },
        content =
        { paddingValues ->
            AddNoteView(navController)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteView(navController: NavHostController){
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier.width(screenWidth).height(screenHeight).background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
            value = title ,
            onValueChange = {
                title = it
            },
            placeholder = { Text(text = stringResource(id = R.string.title)) },
            label ={ Text(text = stringResource(id = R.string.enter_title),color = MaterialTheme.colorScheme.onBackground, fontSize = 10.sp) },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier =Modifier.height(screenHeight/2).padding(all = 8.dp).fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            label ={ Text(text = stringResource(id = R.string.description),color = MaterialTheme.colorScheme.onBackground, fontSize = 10.sp) },
            placeholder = { Text(text = stringResource(id = R.string.enter_description)) },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary,fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        GradientButton(
            text = stringResource(id = R.string.add), textColor = Color.White, gradient = Brush.horizontalGradient(
            colors = listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.inversePrimary)
        ), width = screenWidth / 2
        ) {

        }
    }
}
@OptIn(ExperimentalPagingApi::class)
@Preview(showBackground = true)
@Composable
fun Profile(){
    AddNoteScreen(rememberNavController())
}