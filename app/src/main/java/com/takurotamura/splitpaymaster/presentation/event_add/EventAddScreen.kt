package com.takurotamura.splitpaymaster.presentation.event_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.takurotamura.splitpaymaster.presentation.ScreenRoute
import com.takurotamura.splitpaymaster.presentation.event_add.component.EventDatePicker
import com.takurotamura.splitpaymaster.presentation.event_add.component.MemberInputForm
import com.takurotamura.splitpaymaster.presentation.event_add.component.MemberItem

@Composable
fun EventAddScreen(
    navController: NavController,
    viewModel: EventAddScreenViewModel = hiltViewModel(),
) {
    if (viewModel.showValidationErrorDialog) {
        ValidationErrorDialog(viewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "新規イベント作成") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "戻る")
                }    
            },
            actions = {
                IconButton(
                    onClick = {
                        try {
                            viewModel.validateForm()
                        } catch (e: IllegalArgumentException) {
                            viewModel.errorMessage = e.message ?: ""
                            viewModel.showValidationErrorDialog = true
                            return@IconButton
                        }
                        viewModel.createEvent()
                        navController.navigate(ScreenRoute.EventListScreen.route)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "決定")
                }
            }
        )

        // イベント名入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.eventName,
                onValueChange = { viewModel.eventName = it },
                label = { Text(text = "イベント名") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
        }

        // イベント日時入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            EventDatePicker()
        }

        // イベント参加者入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            MemberInputForm()
        }

        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
        ) {
            LazyColumn {
                items(viewModel.memberList) {member ->
                    MemberItem(name = member) {
                        viewModel.memberList = viewModel.memberList.filterNot { it == member }
                    }
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun ValidationErrorDialog(viewModel: EventAddScreenViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.showValidationErrorDialog = false },
        title = { Text(text = "入力エラー") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = viewModel.errorMessage)
            }
        },
        confirmButton = {
            Button(onClick = { viewModel.showValidationErrorDialog = false }) {
                Text(text = "閉じる")
            }
        },
    )
}