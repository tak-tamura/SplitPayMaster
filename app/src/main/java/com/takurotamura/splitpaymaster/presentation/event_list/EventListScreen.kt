package com.takurotamura.splitpaymaster.presentation.event_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.takurotamura.splitpaymaster.presentation.ScreenRoute
import com.takurotamura.splitpaymaster.presentation.event_list.component.EventList
import com.takurotamura.splitpaymaster.presentation.event_list.component.SearchBar

@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventListScreenViewModel = hiltViewModel(),
) {
    if (viewModel.showDeleteConfirmDialog) {
        DeleteConfirmDialog(viewModel)
    }

    Scaffold(
        topBar = {
            SearchBar(
                searchText = viewModel.query,
                onSearchTextChanged = { viewModel.query = it },
                onDone = { viewModel.searchEvents() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreenRoute.EventAddScreen.route) },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
            }
        },
        modifier = Modifier.padding(16.dp),
    ) { paddingValues ->
        val events by viewModel.eventsLiveData.observeAsState(initial = emptyList())
        EventList(
            events = events,
            onClickRow = {
                navController.navigate(ScreenRoute.EventDetailScreen.route + "/${it.eventId}")
            },
            onClickDelete = {
                viewModel.eventToDelete = it
                viewModel.showDeleteConfirmDialog = true
            },
            paddingValues = paddingValues
        )
    }
}

@Composable
fun DeleteConfirmDialog(viewModel: EventListScreenViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.showDeleteConfirmDialog = false },
        title = { Text(text = "確認") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "${viewModel.eventToDelete.eventName}を削除しますか？")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.deleteEvent()
                viewModel.showDeleteConfirmDialog = false
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = { viewModel.showDeleteConfirmDialog = false }) {
                Text(text = "キャンセル")
            }
        }
    )
}
