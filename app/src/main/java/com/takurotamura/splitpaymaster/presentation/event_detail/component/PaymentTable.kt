package com.takurotamura.splitpaymaster.presentation.event_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import com.takurotamura.splitpaymaster.presentation.event_detail.EventDetailScreenViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun PaymentTable(
    viewModel: EventDetailScreenViewModel = hiltViewModel()
) {
    if (viewModel.showPaymentDeleteConfirmDialog) {
        PaymentDeleteConfirmDialog(viewModel)
    }

    val payments by viewModel.paymentsLiveData.observeAsState(initial = emptyList())
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray),
    ) {
        LazyColumn {
            item {
                TableHeader()
                Divider(color = Color.Black, thickness = 1.dp)
            }
            items(payments) {paymentWithMembers ->
                TableRow(paymentWithMembers = paymentWithMembers, viewModel = viewModel)
                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "日付",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "項目名",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "金額",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(48.dp)) // 削除ボタン分のスペース
    }
}

@Composable
fun TableRow(
    paymentWithMembers: PaymentWithMembers,
    viewModel: EventDetailScreenViewModel,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd").withZone(ZoneId.systemDefault())
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "支払い詳細") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "日付",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(text = paymentWithMembers.payment.paymentDate.format(dateFormatter))
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(
                        text = "項目",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(text = paymentWithMembers.payment.label)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "支払った人",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(text = paymentWithMembers.payers.joinToString(separator = ",") { it.name })

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "借りた人",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(text = paymentWithMembers.payees.joinToString(separator = ",") {it.name })
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "閉じる")
                }
            },
        )
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(8.dp)
    ) {
        Text(
            text = paymentWithMembers.payment.paymentDate.format(dateFormatter), 
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )
        Text(
            text = paymentWithMembers.payment.label,
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )
        Text(
            text = "${paymentWithMembers.payment.amount}",
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        )
        IconButton(
            onClick = {
                viewModel.paymentToDelete = paymentWithMembers.payment
                viewModel.showPaymentDeleteConfirmDialog = true
            },
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
        }
    }
}

@Composable
fun PaymentDeleteConfirmDialog(viewModel: EventDetailScreenViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.showPaymentDeleteConfirmDialog = false },
        title = { Text(text = "確認") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "${viewModel.paymentToDelete.label}を削除しますか？")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.deletePayment()
                viewModel.showPaymentDeleteConfirmDialog = false
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = { viewModel.showPaymentDeleteConfirmDialog = false }) {
                Text(text = "キャンセル")
            }
        }
    )
}