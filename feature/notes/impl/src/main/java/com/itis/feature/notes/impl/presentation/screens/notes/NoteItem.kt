package com.itis.feature.notes.impl.presentation.screens.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itis.common.compose.DateAndTime
import com.itis.common.compose.Description
import com.itis.common.compose.SimpleText
import com.itis.common.compose.SubtitleBox
import com.itis.common.utils.DateFormatter
import com.itis.feature.notes.api.presentation.model.NoteUiModel

@Composable
fun NoteItem(
    note: NoteUiModel,
    dateFormatter: DateFormatter,
    onDeleteClicked: (NoteUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDeleteButtonVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isDeleteButtonVisible = !isDeleteButtonVisible },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            SubtitleBox(
                text = note.title.toString(),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Description(
                text = note.text.toString(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateAndTime(
                text = dateFormatter.formatDateTime(
                    dateFormatter.formatEpochSecondsToDate(note.creationTime)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = isDeleteButtonVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { onDeleteClicked(note) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        SimpleText(text = "Delete Note")
                    }
                }
            }
        }
    }
}