package com.camachoyury.kmovie.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.camachoyury.kmovie.Greeting
import com.camachoyury.kmovie.Injector
import com.camachoyury.kmovie.android.composables.BottomView
import com.camachoyury.kmovie.android.composables.KToolBar
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val topBarState = remember { mutableStateOf(true) }
                val bottomBarState = remember { mutableStateOf(true) }
                val fabState = remember { mutableStateOf(true) }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    val scope = rememberCoroutineScope()
                    val homeViewModel = Injector.homeViewModel
                    homeViewModel.loadItems(scope)
                    Scaffold(
                        scaffoldState = scaffoldState,
                        content = { padding ->

                            HomeScreen(homeViewModel)
                        },
                        topBar = {
                            KToolBar(
                                scaffoldState,
                                topBarState
                            ) { Text("KMovie ") }
                        },
                        bottomBar = { BottomBar(bottomBarState) },
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = {
                            if (fabState.value) {
                                FloatingActionButton(scaffoldState)
                            }
                        },
                        drawerContent = { DrawerContent() },
                    )
                }


            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Composable
fun BottomBar(state: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar(modifier = Modifier.background(Color.Black)) { BottomView() }
        }
    )
}

@Composable
fun FloatingActionButton(scaffoldState: ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material.FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,
        onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("FAB Action Button Clicked")
            }
        }
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "description"
            )
        }
    }
}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Drawer Content")
    }
}
@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
