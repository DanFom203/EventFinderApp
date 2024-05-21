package com.itis.feature.events.impl.presentation.screens.event_info

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.common.base.BaseFragment
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.DateFormatter
import com.itis.common.utils.gone
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.databinding.FragmentEventInfoBinding
import com.itis.feature.events.impl.di.EventsFeatureComponent
import com.itis.feature.events.impl.presentation.screens.events.EventsViewModel
import com.itis.feature.events.impl.utils.Constants
import com.itis.feature.events.impl.utils.Keys
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventInfoFragment: BaseFragment<EventInfoViewModel>(R.layout.fragment_event_info) {

    private val viewBinding: FragmentEventInfoBinding by viewBinding(FragmentEventInfoBinding::bind)

    @Inject
    lateinit var resManager: ResourceManager

    @Inject
    lateinit var dateFormatter: DateFormatter

    override fun initViews() {
        viewModel.initialize(arguments?.getInt(Keys.EVENT_ID_KEY) ?: Constants.EMPTY_INT_DATA)
    }

    override fun inject() {
        FeatureUtils.getFeature<EventsFeatureComponent>(this, EventsFeatureApi::class.java)
            .eventInfoComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: EventInfoViewModel) {
        with(viewModel) {

            currentEventInfoFlow.observe { eventData ->
                eventData?.let {
                    with(viewBinding) {
                        val imagesContainer: LinearLayout = imagesContainer
                        val eventImages = eventData.images

                        for (eventImage in eventImages) {
                            val imageView = ImageView(context)
                            imageView.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                setMargins(8, 4, 8, 4)
                            }
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                            Glide.with(requireContext()).load(eventImage).into(imageView)
                            imagesContainer.addView(imageView)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    companion object {
        fun createBundle(
            eventId: Int,
        ): Bundle {
            return bundleOf(
                Keys.EVENT_ID_KEY to eventId,
            )
        }
    }

}