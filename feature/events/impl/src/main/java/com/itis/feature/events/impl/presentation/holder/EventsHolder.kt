package com.itis.feature.events.impl.presentation.holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.common.core.resources.ResourceManager
import com.itis.common.utils.DateFormatter
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.databinding.ItemEventBinding
import com.itis.feature.events.api.presentation.model.EventUiModel
import com.itis.feature.events.impl.utils.DescriptionFormatter

class EventsHolder (
    private val binding: ItemEventBinding,
    private val glide: RequestManager,
    private val actionNext: (EventUiModel) -> Unit,
    private val dateFormatter: DateFormatter,
    private val resManager: ResourceManager
): RecyclerView.ViewHolder(binding.root) {
    private var eventUiModel: EventUiModel? = null
    private val descriptionFormatter = DescriptionFormatter()

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
            eventDescriptionTv.text = descriptionFormatter.format(eventUiModel.description)
            startDateTv.text = dateFormatter.verifyStartDate(eventUiModel.startDate)
            endDateTv.text = dateFormatter.verifyEndDate(eventUiModel.endDate)
            favouritesCommentsTv.text = buildString {
                append(resManager.getString(R.string.favorites))
                append(" ")
                append(eventUiModel.favoritesCount)
                append(" | ")
                append(resManager.getString(R.string.comments))
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