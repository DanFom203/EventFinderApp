package com.itis.feature.notes.impl.presentation.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.itis.common.base.BaseComposeFragment
import com.itis.common.compose.AppTheme
import com.itis.common.compose.GradientBackgroundBox
import com.itis.common.compose.TitleBox
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.DateFormatter
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.R
import com.itis.feature.notes.impl.di.NotesFeatureComponent
import com.itis.feature.notes.impl.presentation.screens.notes.intent.NotesIntent
import com.itis.feature.notes.impl.presentation.screens.notes.state.NotesState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesFragment: BaseComposeFragment<NotesViewModel>() {

    @Inject
    lateinit var resManager: ResourceManager

    @Inject
    lateinit var dateFormatter: DateFormatter

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
                    NotesScreen(viewModel, dateFormatter, resManager)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sendIntent(NotesIntent.LoadNotes)
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<NotesFeatureComponent>(this, NotesFeatureApi::class.java)
            .notesComponentFactory()
            .create(this)
            .inject(this)
    }

}

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    dateFormatter: DateFormatter,
    resManager: ResourceManager
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.sendIntent(NotesIntent.OpenAddNoteScreen)  }) {
                Icon(Icons.Default.Add, contentDescription = resManager.getString(R.string.add_note))
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        GradientBackgroundBox {
            Column {
                TitleBox(
                    text = resManager.getString(R.string.notes_screen).uppercase(),
                    modifier = Modifier.padding(16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (state) {
                        is NotesState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        is NotesState.Empty -> {
                            Text(
                                text = resManager.getString(R.string.no_notes),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }

                        is NotesState.Success -> {
                            val notes = (state as NotesState.Success).notes
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                items(notes) { note ->
                                    NoteItem(
                                        note = note,
                                        dateFormatter = dateFormatter,
                                        onDeleteClicked = {
                                            viewModel.sendIntent(
                                                NotesIntent.DeleteNote(note.creationTime)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        is NotesState.Error -> {
                            Text(
                                text = "Error: ${(state as NotesState.Error).message}",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
