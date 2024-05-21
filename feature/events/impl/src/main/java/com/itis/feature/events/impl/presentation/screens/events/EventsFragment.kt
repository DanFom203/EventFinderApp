package com.itis.feature.events.impl.presentation.screens.events

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.common.base.BaseFragment
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.DateFormatter
import com.itis.common.utils.gone
import com.itis.common.utils.show
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.databinding.FragmentEventsBinding
import com.itis.feature.events.impl.di.EventsFeatureComponent
import com.itis.feature.events.impl.presentation.adapter.EventsAdapter
import com.itis.feature.events.impl.presentation.model.EventUiModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsFragment : BaseFragment<EventsViewModel>(R.layout.fragment_events) {

    private val viewBinding: FragmentEventsBinding by viewBinding(FragmentEventsBinding::bind)
    private var eventsAdapter: EventsAdapter? = null

    @Inject
    lateinit var resManager: ResourceManager

    @Inject
    lateinit var dateFormatter: DateFormatter

    override fun initViews() {
        val location = "kzn"
        viewModel.initialize(location = location)
        with(viewBinding) {
            loadingProgressBar.show()
            eventsAdapter = EventsAdapter(
                actionNext = ::onEventClicked,
                resManager = resManager,
                glide = Glide.with(requireContext()),
                dateFormatter = dateFormatter
            )
            eventsRv.adapter = eventsAdapter
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<EventsFeatureComponent>(this, EventsFeatureApi::class.java)
            .eventsComponentFactory().create(this).inject(this)
    }

    override suspend fun subscribe(viewModel: EventsViewModel) {
        with(viewModel) {

            currentEventsFlow.observe { eventsData ->
                eventsData?.let {
                    with(viewBinding) {
                        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        eventsRv.layoutManager = layoutManager
                        eventsRv.adapter = eventsAdapter
                        eventsAdapter?.submitList(it.events)
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

    private fun onEventClicked(eventUiModel: EventUiModel) {
        viewModel.openEventInfoScreen(eventUiModel)
    }
}