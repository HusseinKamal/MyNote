package com.hussein.mynote.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hussein.mynote.R
import com.hussein.mynote.model.Note
import com.hussein.mynote.model.ResourceState
import com.hussein.mynote.screens.ui.CustomTopAppBar
import com.hussein.mynote.screens.ui.EmptyData
import com.hussein.mynote.screens.ui.Loader
import com.hussein.mynote.ui.theme.Purple40
import com.hussein.mynote.util.Routes
import com.hussein.mynote.viewmodel.NoteViewModel
import kotlinx.coroutines.delay

@ExperimentalPagingApi
@Composable
fun HomeScreen(navController: NavHostController, noteViewModel: NoteViewModel = hiltViewModel()) {
    val notes by noteViewModel.notes.collectAsState()// ---> call notes.value direct
    Scaffold(
        topBar = {
            CustomTopAppBar(navController = navController, routePopup = Routes.NOTES_ROUTE, title = stringResource(
                id = R.string.home
            ), hasBackArrow = false)
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = Purple40,
                onClick = {
                    navController.navigate(Routes.ADD_NOTE_SCREEN)//Go to Add new note screen
                }
            ) {
                Icon(Icons.Filled.Add,"Add Note", tint = White)
            }
        },
        content = {padding ->
            Column(
                modifier = Modifier
                    .padding(padding)){
                when(notes){
                    is ResourceState.Success -> {
                        //Get data if founded
                        val response = (notes as ResourceState.Success).data
                        ListContent(items = response)
                    }
                    is ResourceState.Loading -> {
                        //Add Loader view here
                        Loader()
                    }
                    is ResourceState.Error -> {
                        //Add Not Data in Error
                        EmptyData()
                    }
                }

            }
        }, backgroundColor = White
    )
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun ListContent(items:List<Note>,noteViewModel: NoteViewModel= hiltViewModel()) {
   /* val notes by noteViewModel.notes.collectAsState()// ---> call notes.value direct
    when(notes){
        is ResourceState.Success -> {
            //Get data if founded
            val response = (notes as ResourceState.Success).data
            ListContent(items = response)
        }
        is ResourceState.Loading -> {
            //Add Loader view here
            Loader()
        }
        is ResourceState.Error -> {
            //Add Not Data in Error
            EmptyData()
        }
    }*/
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
                    },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            items(
                count = items.size,
            ) { index ->
                val item = items[index]
                NoteItem(note = item,noteViewModel = noteViewModel)
            }
        }
    }


}

@Composable
fun NoteItem(note: Note,noteViewModel: NoteViewModel= hiltViewModel()){
    val iconSize = 20.dp
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = Modifier
                .fillMaxSize(),
            elevation = 3.dp,
            backgroundColor = MaterialTheme.colorScheme.primary,
            border = BorderStroke(width = 3.dp, color = Purple40),
            shape = RoundedCornerShape(20.dp),
        )
        {
            Column(modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //Title
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(note.title)
                    }
                },
                    color= White,
                    fontSize = 18.sp,//androidx.compose.material.MaterialTheme.typography.caption.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
                //Description
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(note.description)
                    }
                },
                    color= White,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp),
                    elevation = 3.dp,
                    backgroundColor = Purple40,
                    //border = BorderStroke(width = 2.dp, color = Purple40),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(all = 4.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Time : ${note.time}")
                                }
                            },
                            color = White,
                            fontSize = 14.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }

        //Icon for Removing note
        IconButton(
            onClick = {
                noteViewModel.deleteNote(note = note)
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(Purple40)
                .size(iconSize)
                .align(Alignment.TopEnd)
                .padding(2.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

}
