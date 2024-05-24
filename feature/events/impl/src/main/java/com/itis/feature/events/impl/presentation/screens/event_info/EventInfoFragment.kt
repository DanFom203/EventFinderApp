package com.itis.feature.events.impl.presentation.screens.event_info

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.common.base.BaseFragment
import com.itis.common.core.resources.ResourceManager
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.Constants
import com.itis.common.utils.DateFormatter
import com.itis.common.utils.gone
import com.itis.common.utils.show
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.databinding.FragmentEventInfoBinding
import com.itis.feature.events.impl.di.EventsFeatureComponent
import com.itis.feature.events.impl.presentation.model.EventInfoUiModel
import com.itis.feature.events.impl.utils.DescriptionFormatter
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

    @Inject
    lateinit var cityFormatter: CityFormatter

    private val descriptionFormatter = DescriptionFormatter()

    override fun initViews() {
        viewModel.initialize(arguments?.getInt(Keys.EVENT_ID_KEY)
            ?: Constants.EMPTY_INT_DATA)
        viewBinding.loadingProgressBar.show()
    }

    override fun inject() {
        FeatureUtils.getFeature<EventsFeatureComponent>(this, EventsFeatureApi::class.java)
            .eventInfoComponentFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: EventInfoViewModel) {
        with(viewModel) {

            currentEventInfoFlow.observe { eventData ->
                eventData?.let {
                    changeAddToFavouritesBtnStatus(eventData.isLiked)
                    setTextFields(eventData = it)
                    setImages(eventData = it)
                    setButtons(eventData = it)
                    viewBinding.loadingProgressBar.gone()
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

    private fun setImages(eventData: EventInfoUiModel) {
        val imagesContainer: LinearLayout = viewBinding.imagesContainer
        val eventImages = eventData.images

        val imageWidth = resources.getDimensionPixelSize(R.dimen.event_image_width)
        val imageHeight = resources.getDimensionPixelSize(R.dimen.event_image_height)
        val imageMargin = resources.getDimensionPixelSize(R.dimen.image_margin)

        for (eventImage in eventImages) {
            val imageView = ImageView(context)
            imageView.layoutParams = LinearLayout.LayoutParams(
                imageWidth,
                imageHeight
            ).apply {
                setMargins(imageMargin, imageMargin / 2, imageMargin, imageMargin / 2)
            }
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            Glide.with(requireContext())
                .load(eventImage.imageUrl)
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.error)
                .into(imageView)
            imagesContainer.addView(imageView)
        }
    }

    private fun changeAddToFavouritesBtnStatus(isChecked: Boolean) {
        with(viewBinding) {
            if (isChecked) {
                deleteFavoriteButton.show()
                addFavoriteButton.gone()
            } else {
                addFavoriteButton.show()
                deleteFavoriteButton.gone()
            }
        }
    }

    private fun setTextFields(eventData: EventInfoUiModel) {
        with(viewBinding) {
            title.text = eventData.title
            address.text = buildString {
                append(resManager.getString(R.string.event_address))
                append(" ")
                append(eventData.address)
            }
            location.text = buildString {
                append(resManager.getString(R.string.event_location))
                append(" ")
                append(cityFormatter.abbreviationToCity(eventData.location))
            }
            description.text = buildString {
                append(resManager.getString(R.string.event_description_label))
                append("\n")
                append("  ")
                append(descriptionFormatter.format(eventData.description))
            }
            categoriesLabel.text = buildString {
                append(resManager.getString(R.string.categories))
                append(" ")
                eventData.categories.forEach { category ->
                    append(category)
                    append("\n")
                }
            }
            startDate.text = buildString {
                append(resManager.getString(R.string.start_date))
                append(" ")
                append(dateFormatter.verifyStartDate(eventData.startDate))
            }
            endDate.text = buildString {
                append(resManager.getString(R.string.end_date))
                append(" ")
                append(dateFormatter.verifyEndDate(eventData.endDate))
            }
            ageRestriction.text = buildString {
                append(resManager.getString(R.string.age_restriction))
                append(" ")
                append(eventData.ageRestriction)
            }
            isFree.text = buildString {
                append(resManager.getString(R.string.is_free))
                append(" ")
                append(eventData.isFree)
            }
            imagesLabel.text = resManager.getString(R.string.images)
            favoritesCount.text = buildString {
                append(resManager.getString(R.string.favorites_count))
                append(" ")
                append(eventData.favoritesCount)
            }
            commentsCount.text = buildString {
                append(resManager.getString(R.string.comments_count))
                append(" ")
                append(eventData.commentsCount)
            }
        }
    }

    private fun setButtons(eventData: EventInfoUiModel) {
        with(viewBinding) {
            addFavoriteButton.setOnClickListener {
                viewModel.saveToFirestoreDb(eventData.id)
            }
            deleteFavoriteButton.setOnClickListener {
                viewModel.deleteFromFirestoreDb(eventData.id)
            }
            goBackButton.setOnClickListener {
                viewModel.openEvents()
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