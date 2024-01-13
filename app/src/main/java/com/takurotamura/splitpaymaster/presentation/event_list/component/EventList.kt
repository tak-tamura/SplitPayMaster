package com.takurotamura.splitpaymaster.presentation.event_list.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.takurotamura.splitpaymaster.data.entity.Event

@Composable
fun EventList(
    events: List<Event>,
    onClickRow: (Event) -> Unit,
    onClickDelete: (Event) -> Unit,
    paddingValues: PaddingValues,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(events) {
            EventRow(event = it, onClickRow = onClickRow, onClickDelete = onClickDelete)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}