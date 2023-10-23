package com.hussein.mynote.screens

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.hussein.mynote.model.validator.NoteFormEvent
import com.hussein.mynote.screens.ui.CustomTopAppBar
import com.hussein.mynote.screens.ui.GradientButton
import com.hussein.mynote.util.Constant
import com.hussein.mynote.util.Routes
import com.hussein.mynote.viewmodel.NoteViewModel
import java.util.Calendar

@ExperimentalPagingApi
@Composable
fun AddNoteScreen(navController: NavHostController,noteViewModel: NoteViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
                 CustomTopAppBar(navController = navController, routePopup = Routes.NOTES_ROUTE, title = stringResource(
                     id = R.string.add
                 ), hasBackArrow = true)
        },
        content =
        { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)) {// paddingValues ->
                AddNoteView(navController, noteViewModel)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteView(navController: NavHostController,noteViewModel: NoteViewModel= hiltViewModel()) {
    //var title by rememberSaveable { mutableStateOf("") }
    //var description by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val mContext = LocalContext.current
    val state = noteViewModel.state
    LaunchedEffect(key1 = mContext){
        noteViewModel.validateEvents.collect{event ->
            when(event){
                is NoteViewModel.ValidationEvent.Success ->
                {
                    val note = event.state
                    addNoteInDB(
                        navController = navController,
                        note = note,
                        noteViewModel = noteViewModel
                    )
                }
            }
        }// ---> call notes.value direct

    }
    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int -> state.time = "$mHour:${Constant.addZeroBefore(mMinute.toString())}"
        }, mHour, mMinute, false
    )
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())//Add scroll enable in column
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
            value = state.title,
            onValueChange = {
                noteViewModel.onEvent(NoteFormEvent.TitleChanged(it))
            },
            isError = state.titleError !=null ,
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
        if(state.titleError !=null){
            Text(text = state.titleError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .height(screenHeight / 2)
                .padding(all = 8.dp)
                .fillMaxWidth(),
            value = state.description,
            onValueChange = {
                noteViewModel.onEvent(NoteFormEvent.DescriptionChanged(it))
            },
            isError = state.descriptionError !=null ,
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
        if(state.descriptionError !=null){
            Text(text = state.descriptionError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .clickable {
                    //Show Time picker dialog
                    mTimePickerDialog.show()
                    //TimePickerDialog(content = {}, toggle = {}, onConfirm = {}, onCancel = {})
                },
            value = state.time,
            enabled = false,
            onValueChange = {
                noteViewModel.onEvent(NoteFormEvent.TimeChanged(it))
            },
            isError = state.timeError !=null ,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            placeholder = { Text(text = stringResource(id = R.string.time_for_note)) },
            label = {
                Text(
                    text = stringResource(id = R.string.time_for_note),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
        )
        if(state.timeError !=null){
            Text(text = state.timeError!!, color = MaterialTheme.colorScheme.error)
        }
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
                noteViewModel.onEvent(NoteFormEvent.Submit)
            }
        )

    }
}
fun addNoteInDB(
    navController: NavHostController,
    note: Note,
    noteViewModel: NoteViewModel
) {
    noteViewModel.addNote(note = note)
    navController.navigate(Routes.NOTES_ROUTE) {
        popUpTo(Routes.NOTES_ROUTE) {
            inclusive = true
        }
    }
}