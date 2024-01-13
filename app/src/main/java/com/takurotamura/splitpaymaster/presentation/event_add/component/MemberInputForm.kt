package com.takurotamura.splitpaymaster.presentation.event_add.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.takurotamura.splitpaymaster.presentation.event_add.EventAddScreenViewModel

@Composable
fun MemberInputForm(viewModel: EventAddScreenViewModel = hiltViewModel()) {
    OutlinedTextField(
        value = viewModel.memberName,
        onValueChange = { viewModel.memberName = it },
        label = { Text(text = "イベント参加者") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(5.dp))

    Button(
        onClick = {
            if (viewModel.memberName.isNotBlank()) {
                viewModel.memberList = viewModel.memberList + viewModel.memberName
                viewModel.memberName = ""
            }
        }
    ) {
        Text(text = "追加")
    }
}