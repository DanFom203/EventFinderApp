package com.itis.feature.notes.impl.presentation.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.itis.common.utils.DateFormatter
import com.itis.feature.notes.impl.databinding.ItemNoteBinding
import com.itis.feature.notes.impl.presentation.model.NoteUiModel

class NotesHolder(
    private val viewBinding: ItemNoteBinding,
    private val dateFormatter: DateFormatter,
    private val onDeleteClicked: ((NoteUiModel) -> Unit)
): RecyclerView.ViewHolder(viewBinding.root) {
    private var noteUiModel: NoteUiModel? = null
    private var isDeleteButtonVisible: Boolean = false

    init {
        viewBinding.root.setOnLongClickListener {
            showDeleteButton()
            true
        }
        viewBinding.deleteNoteBtn.setOnClickListener {
            this.noteUiModel?.let(onDeleteClicked)
        }
    }

    fun onBind(noteUiModel: NoteUiModel) {
        this.noteUiModel = noteUiModel
        with(viewBinding) {
            titleTv.text = noteUiModel.title
            textTv.text = noteUiModel.text
            timeTv.text = dateFormatter.formatDateTime(
                dateFormatter.formatEpochSecondsToDate(noteUiModel.creationTime)
            )
        }
    }

    private fun showDeleteButton() {
        isDeleteButtonVisible = !isDeleteButtonVisible
        viewBinding.deleteNoteBtn.visibility = if (isDeleteButtonVisible) View.VISIBLE else View.GONE
    }
}