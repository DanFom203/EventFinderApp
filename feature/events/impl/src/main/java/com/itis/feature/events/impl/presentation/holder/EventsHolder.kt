package com.itis.feature.events.impl.presentation.holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.common.utils.DateFormatter
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.databinding.ItemEventBinding
import com.itis.feature.events.impl.presentation.model.EventUiModel

class EventsHolder (
    private val binding: ItemEventBinding,
    private val glide: RequestManager,
    private val actionNext: (EventUiModel) -> Unit,
    private val dateFormatter: DateFormatter
): RecyclerView.ViewHolder(binding.root) {
    private var eventUiModel: EventUiModel? = null

    init {
        itemView.setOnClickListener {
            eventUiModel?.also(actionNext)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onBind(eventUiModel: EventUiModel) {
        this.eventUiModel = eventUiModel
        with(binding) {
            textViewEventTitle.text = eventUiModel.title
            eventDescriptionTv.text = eventUiModel.description
            startDateTv.text = dateFormatter.formatDateTime(
                dateFormatter.formatEpochSecondsToDate(eventUiModel.startDate)
            )
            endDateTv.text = dateFormatter.formatDateTime(
                dateFormatter.formatEpochSecondsToDate(eventUiModel.endDate)
            )
            favouritesCommentsTv.text = buildString {
                append("Favourites:")
                append(" ")
                append(eventUiModel.favoritesCount)
                append(" | ")
                append("Comments:")
                append(" ")
                append(eventUiModel.commentsCount)
            }
            glide.load(eventUiModel.imageUrl)
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.error)
                .into(eventIv)
        }
    }
}