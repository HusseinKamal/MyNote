package com.hussein.mynote.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.hussein.mynote.R
import com.hussein.mynote.model.Note
import com.hussein.mynote.model.ResourceState
import com.hussein.mynote.screens.ui.CustomTopAppBar
import com.hussein.mynote.screens.ui.GradientButton
import com.hussein.mynote.util.Routes
import com.hussein.mynote.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date

@ExperimentalPagingApi
@Composable
fun AddNoteScreen(navController: NavHostController,noteViewModel: NoteViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
                 CustomTopAppBar(navController = navController, routePopup = Routes.NOTES_ROUTE, title = stringResource(
                     id = R.string.add
                 ), hasBackArrow = true)
           /* TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Home",
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.NOTES_ROUTE){
                        popUpTo(Routes.NOTES_ROUTE) {
                            inclusive = true
                        }
                    }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )*/
        },
        content =
        { paddingValues ->
            AddNoteView(navController,noteViewModel)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteView(navController: NavHostController,noteViewModel: NoteViewModel) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
   /* val scope = rememberCoroutineScope()
    val notes by noteViewModel.notes.collectAsState()// ---> call notes.value direct
    when(notes){
        is ResourceState.Success -> {
            //Get data if founded
            val response = (notes as ResourceState.Success).data
            val items = response[0].description
        }
        is ResourceState.Loading -> {
            //Add Loader view here

        }
        is ResourceState.Error -> {

        }
    }*/
    //val notes = noteViewModel.notes.collectAsState() // You should call notes.value in when code
    Column(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight)
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            value = title,
            onValueChange = {
                title = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            placeholder = { Text(text = stringResource(id = R.string.title)) },
            label = {
                Text(
                    text = stringResource(id = R.string.enter_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .height(screenHeight / 2)
                .padding(all = 8.dp)
                .fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.description),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.enter_description)) },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        GradientButton(
            text = stringResource(id = R.string.add),
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.inversePrimary
                )
            ),
            width = screenWidth / 2,
            onClick = {
                /* when(notes){
                    is ResourceState.Success -> {

                    }
                    is ResourceState.Loading -> {

                    }
                    is ResourceState.Error -> {

                    }
                }*/
                val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
                // on below line we are creating a variable for
                // current date and time and calling a simple
                // date format in it.
                val currentDateAndTime = sdf.format(Date())
                val note = Note(
                    title = title,
                    description = description,
                    date = currentDateAndTime,
                    time = currentDateAndTime
                )
                addNoteInDB(
                    context = context,
                    navController = navController,
                    note = note,
                    noteViewModel = noteViewModel
                )

            }
        )

    }
}
fun addNoteInDB(
    context: Context,
    navController: NavHostController,
    note: Note,
    noteViewModel: NoteViewModel
) {
    noteViewModel.addNote(note = note)
    navController.navigate(Routes.NOTES_ROUTE)
   /* val notes = noteViewModel.notes.collectLatest {
        val item =it[0]
    }*/
}