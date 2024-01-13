package com.takurotamura.splitpaymaster.presentation.event_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.takurotamura.splitpaymaster.R
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.presentation.ScreenRoute
import com.takurotamura.splitpaymaster.presentation.event_detail.component.PaymentTable

@Composable
fun EventDetailScreen(
    navController: NavController,
    viewModel: EventDetailScreenViewModel = hiltViewModel(),
) {
    val event by viewModel.eventLiveData.observeAsState()
    event?.let {
        EventDetailView(navController, it, viewModel)
    }
}

@Composable
fun EventDetailView(
    navController: NavController,
    event: EventWithMembers,
    viewModel: EventDetailScreenViewModel,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreenRoute.PaymentAddScreen.route + "/${event.event.eventId}") },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "支払いを追加")
            }
        }
    ) {
        if (viewModel.showPaymentResultDialog) {
            PaymentResult(event.members, viewModel)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            TopAppBar(
                title = { Text(text = event.event.eventName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.showPaymentResultDialog = true }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_payments_24),
                            contentDescription = "精算"
                        )
                    }
                }
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                PaymentTable()
            }
        }
    }
}

@Composable
fun PaymentResult(
    members: List<Member>,
    viewModel: EventDetailScreenViewModel
) {
    val paymentDetails = viewModel.calculatePayments(members)
    AlertDialog(
        onDismissRequest = { viewModel.showPaymentResultDialog = false },
        title = { Text(text = "精算") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                paymentDetails.forEach {
                    Text(text = "${it.from} が ${it.to} に${it.amount}円払う")
                }
            }
        },
        confirmButton = {
            Button(onClick = { viewModel.showPaymentResultDialog = false }) {
                Text(text = "閉じる")
            }
        }
    )
}