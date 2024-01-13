package com.takurotamura.splitpaymaster.presentation.payment_add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.presentation.payment_add.PaymentAddScreenViewModel

@Composable
fun MemberSelectBox(
    memberList: List<Member>,
    label: String,
    isPayer: Boolean,
    viewModel: PaymentAddScreenViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }

    Text(text = label)
    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = if (isPayer) {
                viewModel.payerList.joinToString(separator = ",") { it.name }
            } else {
                viewModel.payeeList.joinToString(separator = ",") { it.name }
            },
            onValueChange = {},
            enabled = false,
            modifier = Modifier.clickable { showDialog = true }
        )
    }

    val checkStateMap = memberList.map { it.memberId }.associateWith {
        remember { mutableStateOf(false) }
    }.toMutableMap()
    
    if (showDialog) {
        val tempSelectedList = if (isPayer) viewModel.payerList.toMutableList()
                               else viewModel.payeeList.toMutableList()
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (isPayer) {
                        viewModel.payerList = tempSelectedList
                    } else {
                        viewModel.payeeList = tempSelectedList
                    }
                    showDialog = false
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false}) {
                    Text(text = "キャンセル")
                }
            },
            title = {
                Text(text = label)
            },
            text = {
                LazyColumn {
                    items(memberList) { member ->
                        Row() {
                            Checkbox(
                                checked = checkStateMap[member.memberId]?.value ?: false,
                                onCheckedChange = { checked ->
                                    checkStateMap[member.memberId]?.let { it.value = checked }
                                    if (checked) tempSelectedList.add(member)
                                    else tempSelectedList.remove(member)
                                }
                            )
                            Text(text = member.name, modifier = Modifier.align(alignment = Alignment.CenterVertically))
                        }
                    }
                }
            }
        )
    }
}