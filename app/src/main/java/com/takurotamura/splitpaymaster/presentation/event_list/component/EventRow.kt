package com.takurotamura.splitpaymaster.presentation.event_list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takurotamura.splitpaymaster.data.entity.Event
import java.time.format.DateTimeFormatter

@Composable
fun EventRow(
    event: Event,
    onClickRow: (Event) -> Unit,
    onClickDelete: (Event) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 5.dp,
    ) {
        Box(
            modifier = Modifier
                .clickable { onClickRow(event) }
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = event.eventName,
                        fontSize = 22.sp,
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "人数")
                        Text(text = "${event.memberCount}人")
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "日付")
                        Text(text = event.eventDate.format(DateTimeFormatter.ISO_DATE))
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    IconButton(onClick = { onClickDelete(event) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
                    }
                }
            }
        }
    }
}