package com.itis.feature.notes.impl.presentation.screens.add_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itis.common.base.BaseComposeFragment
import com.itis.common.compose.AppTheme
import com.itis.common.compose.GradientBackgroundBox
import com.itis.common.compose.TitleBox
import com.itis.common.di.FeatureUtils
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.R
import com.itis.feature.notes.impl.di.NotesFeatureComponent
import kotlinx.coroutines.channels.consumeEach

class AddNoteFragment : BaseComposeFragment<AddNoteViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner.lifecycle)
            )
            setContent {
                AppTheme {
                    AddNoteScreen(
                        viewModel = viewModel,
                        onExit = { viewModel.openNotesScreen() }
                    )
                }
            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<NotesFeatureComponent>(this, NotesFeatureApi::class.java)
            .addNoteComponentFactory()
            .create(this)
            .inject(this)
    }
}

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel,
    onExit: () -> Unit
) {
    val context = LocalContext.current

    GradientBackgroundBox {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleBox(text = stringResource(id = R.string.add_note))

            var title by remember { mutableStateOf("") }
            var text by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                NoteInputCard(
                    value = title,
                    onValueChange = { title = it },
                    hint = stringResource(id = R.string.note_title)
                )

                Spacer(modifier = Modifier.height(16.dp))

                NoteInputCard(
                    value = text,
                    onValueChange = { text = it },
                    hint = stringResource(id = R.string.note_text),
                    maxLines = 15
                )
            }

            Column {
                Button(
                    onClick = {
                        val currentTime = System.currentTimeMillis() / 1000
                        viewModel.addNote(
                            creationTime = currentTime,
                            title = title,
                            text = text
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.save_note))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onExit,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.exit))
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorsChannel.consumeEach { error ->
            val errorMessage = error.message ?: context.getString(R.string.unknown_error)
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun NoteInputCard(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLines: Int = 1
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.small
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = hint) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = maxLines
        )
    }
}