package com.itis.feature.notes.impl.presentation.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.itis.common.base.BaseComposeFragment
import com.itis.common.compose.AppTheme
import com.itis.common.compose.Title
import com.itis.common.compose.md_theme_light_inversePrimary
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.DateFormatter
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.di.NotesFeatureComponent
import com.itis.feature.notes.impl.presentation.holder.NoteItem
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
                    NotesScreen(viewModel, dateFormatter)
                }
            }
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
fun NotesScreen(viewModel: NotesViewModel, dateFormatter: DateFormatter) {
    val notesState by viewModel.currentNotesFlow.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    viewModel.initialize()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.openAddNoteScreen() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(md_theme_light_inversePrimary)
            ) {
                Title(
                    text = "Notes Screen".uppercase(),
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                when {
                    notesState == null -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    notesState!!.isEmpty() -> {
                        Text(
                            text = "No notes available. Click the button to add a new note.",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(notesState!!) { note ->
                                NoteItem(
                                    note = note,
                                    dateFormatter = dateFormatter,
                                    onDeleteClicked = { viewModel.deleteNote(it.creationTime) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
