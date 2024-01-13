package com.takurotamura.splitpaymaster.presentation.payment_add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.takurotamura.splitpaymaster.presentation.ScreenRoute
import com.takurotamura.splitpaymaster.presentation.payment_add.component.MemberSelectBox
import com.takurotamura.splitpaymaster.presentation.payment_add.component.PaymentDatePicker

@Composable
fun PaymentAddScreen(
    navController: NavController,
    viewModel: PaymentAddScreenViewModel = hiltViewModel(),
) {
    val members by viewModel.membersLiveData.observeAsState(initial = emptyList())

    if (viewModel.showValidationErrorDialog) {
        ValidationErrorDialog(viewModel)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "新規支払いを作成") },
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
                        viewModel.createPayment()
                        navController.navigate(ScreenRoute.EventDetailScreen.route + "/${viewModel.eventId}")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "作成")
                }
            }
        )

        // 項目入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.label,
                onValueChange = { viewModel.label = it },
                label = { Text(text = "項目名") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
        }

        // 金額入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.amount.toString(),
                onValueChange = {
                    if (it.isEmpty()) {
                        viewModel.amount = 0
                    } else if (viewModel.amount == 0 && it.endsWith("0")) {
                        viewModel.amount = it.substring(0, it.length - 1).toInt()
                    } else if (it.isDigitsOnly()) {
                        viewModel.amount = it.toInt()
                    }
                },
                label = { Text(text = "金額を入力") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
        }

        // 支払い日時入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            PaymentDatePicker()
        }

        // 支払い者入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            MemberSelectBox(
                memberList = members,
                label = "支払った人を選択",
                isPayer = true
            )
        }

        // 借りた人入力
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            MemberSelectBox(
                memberList = members,
                label = "借りた人を選択",
                isPayer = false
            )
        }
    }
}

@Composable
fun ValidationErrorDialog(viewModel: PaymentAddScreenViewModel) {
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