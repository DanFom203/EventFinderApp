package com.itis.feature.notes.impl.presentation.screens.notes

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.DateFormatter
import com.itis.common.utils.gone
import com.itis.common.utils.show
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.R
import com.itis.feature.notes.impl.databinding.FragmentNotesBinding
import com.itis.feature.notes.impl.di.NotesFeatureComponent
import com.itis.feature.notes.impl.presentation.adapter.NotesAdapter
import com.itis.feature.notes.impl.presentation.model.NoteUiModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesFragment: BaseFragment<NotesViewModel>(R.layout.fragment_notes) {

    private val viewBinding: FragmentNotesBinding by viewBinding(FragmentNotesBinding::bind)
    private var notesAdapter: NotesAdapter? = null

    @Inject
    lateinit var resManager: ResourceManager

    @Inject
    lateinit var dateFormatter: DateFormatter

    override fun initViews() {

        viewModel.initialize()
        with(viewBinding) {
            loadingProgressBar.show()
            notesAdapter = NotesAdapter(
                resManager = resManager,
                dateFormatter = dateFormatter,
                onDeleteClicked = ::onDeleteClicked
            )
            notesRv.adapter = notesAdapter
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<NotesFeatureComponent>(this, NotesFeatureApi::class.java)
            .notesComponentFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: NotesViewModel) {
        with(viewModel) {

            currentNotesFlow.observe { notesData ->
                notesData?.let {
                    with(viewBinding) {
                        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        notesRv.layoutManager = layoutManager
                        notesRv.adapter = notesAdapter
                        notesAdapter?.submitList(it)
                        addNoteBtn.setOnClickListener{
                            openAddNoteScreen()
                        }
                        loadingProgressBar.gone()
                    }
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    viewBinding.loadingProgressBar.gone()
                }
            }
        }
    }

    private fun onDeleteClicked(noteUiModel: NoteUiModel) {
        viewModel.deleteNote(noteUiModel.creationTime)
    }
}