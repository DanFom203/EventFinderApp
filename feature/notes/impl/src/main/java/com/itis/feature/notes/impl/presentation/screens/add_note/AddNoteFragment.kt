package com.itis.feature.notes.impl.presentation.screens.add_note

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.R
import com.itis.feature.notes.impl.databinding.FragmentAddNoteBinding
import com.itis.feature.notes.impl.di.NotesFeatureComponent
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class AddNoteFragment: BaseFragment<AddNoteViewModel>(R.layout.fragment_add_note) {

    private val viewBinding: FragmentAddNoteBinding by viewBinding(FragmentAddNoteBinding::bind)

    override fun initViews() {}

    override fun inject() {
        FeatureUtils.getFeature<NotesFeatureComponent>(this, NotesFeatureApi::class.java)
            .addNoteComponentFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: AddNoteViewModel) {
        with(viewModel) {
            with(viewBinding) {
                saveNoteBtn.setOnClickListener {
                    val currentTime = System.currentTimeMillis() / 1000
                    val noteTitle = titleEt.text.toString()
                    val noteText = textEt.text.toString()
                    addNote(
                        creationTime = currentTime,
                        title = noteTitle,
                        text = noteText
                    )
                }
                exitBtn.setOnClickListener {
                    openNotesScreen()
                }

                lifecycleScope.launch {
                    errorsChannel.consumeEach { error ->
                        val errorMessage = error.message ?: getString(R.string.unknown_error)
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}