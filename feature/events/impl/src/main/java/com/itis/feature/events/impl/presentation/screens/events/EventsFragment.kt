package com.itis.feature.events.impl.presentation.screens.events

import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.presentation.adapter.EventsAdapter

class EventsFragment : BaseFragment<EventsViewModel>(R.layout.fragment_events) {

//    private val viewBinding: FragmentEventsBinding by viewBinding(FragmentEventsBinding::bind)
    private var adapter: EventsAdapter? = null

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun inject() {
        TODO("Not yet implemented")
    }

    override suspend fun subscribe(viewModel: EventsViewModel) {
        TODO("Not yet implemented")
    }
}