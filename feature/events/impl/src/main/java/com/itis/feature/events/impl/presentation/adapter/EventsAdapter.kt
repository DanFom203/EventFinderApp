package com.itis.feature.events.impl.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.DateFormatter
import com.itis.feature.events.impl.R
import com.bumptech.glide.RequestManager
import com.itis.feature.events.impl.databinding.ItemEventBinding
import com.itis.feature.events.impl.presentation.holder.EventsHolder
import com.itis.feature.events.impl.presentation.model.EventUiModel

class EventsAdapter (
    private val glide: RequestManager,
    private val actionNext: (EventUiModel) -> Unit,
    private val resManager: ResourceManager,
    private val dateFormatter: DateFormatter
): ListAdapter<EventUiModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<EventUiModel>() {
        override fun areItemsTheSame(
            oldItem: EventUiModel,
            newItem: EventUiModel
        ): Boolean = (oldItem as? EventUiModel)?.id == (newItem as? EventUiModel)?.id

        override fun areContentsTheSame(
            oldItem: EventUiModel,
            newItem: EventUiModel
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return EventsHolder(
            binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glide = glide,
            actionNext = actionNext,
            dateFormatter = dateFormatter
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? EventsHolder)?.onBind(eventUiModel = getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is EventUiModel -> R.layout.item_event
            else -> throw IllegalArgumentException(resManager.getString(R.string.unknown_error))
        }
}