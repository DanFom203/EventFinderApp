package com.itis.feature.notes.impl.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.DateFormatter
import com.itis.feature.notes.impl.R
import com.itis.feature.notes.impl.databinding.ItemNoteBinding
import com.itis.feature.notes.impl.presentation.holder.NotesHolder
import com.itis.feature.notes.impl.presentation.model.NoteUiModel

class NotesAdapter(
    private val resManager: ResourceManager,
    private val dateFormatter: DateFormatter,
    private val onDeleteClicked: ((NoteUiModel) -> Unit)
): ListAdapter<NoteUiModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<NoteUiModel>() {
        override fun areItemsTheSame(
            oldItem: NoteUiModel,
            newItem: NoteUiModel
        ): Boolean = (oldItem as? NoteUiModel)?.creationTime == (newItem as? NoteUiModel)?.creationTime

        override fun areContentsTheSame(
            oldItem: NoteUiModel,
            newItem: NoteUiModel
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return NotesHolder(
            viewBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            dateFormatter = dateFormatter,
            onDeleteClicked = onDeleteClicked
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? NotesHolder)?.onBind(noteUiModel = getItem(position))

    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is NoteUiModel -> R.layout.item_note
            else -> throw IllegalArgumentException(resManager.getString(R.string.wrong_item_type))
        }
}