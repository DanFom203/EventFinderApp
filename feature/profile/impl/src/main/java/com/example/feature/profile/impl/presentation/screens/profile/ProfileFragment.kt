package com.example.feature.profile.impl.presentation.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.feature.profile.api.di.ProfileFeatureApi
import com.example.feature.profile.impl.R
import com.example.feature.profile.impl.databinding.FragmentUserProfileBinding
import com.example.feature.profile.impl.di.ProfileFeatureComponent
import com.example.feature.profile.impl.presentation.model.ProfileUserUiModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.gone
import com.itis.common.utils.show
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_user_profile) {

    private val viewBinding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)

    @Inject
    lateinit var cityFormatter: CityFormatter

    private lateinit var imagePickLauncher: ActivityResultLauncher<Intent>

    private var selectedImageUri: Uri? = null

    override fun initViews() {
        viewModel.initialize()
        imagePickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.data != null) {
                    selectedImageUri = data.data
                    selectedImageUri?.let { viewModel.updateProfilePicture(it) }
                }
            }
        }
        with(viewBinding) {
            loadingProgressBar.show()
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<ProfileFeatureComponent>(this, ProfileFeatureApi::class.java)
            .profileComponentFactory()
            .create(this)
            .inject(this)
    }

    override suspend fun subscribe(viewModel: ProfileViewModel) {
        with(viewModel) {

            currentUserProfileInfoFlow.observe { profileData ->
                profileData?.let {
                    setTextFields(profileData = it)
                    setImages(profileData = it)
                    setButtons()
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

    private fun setTextFields(profileData: ProfileUserUiModel) {
        with(viewBinding) {
            usernameTv.text = profileData.username
            emailTv.text = profileData.email
            cityTv.text = cityFormatter.abbreviationToCity(profileData.city)
        }
    }

    private fun setImages(profileData: ProfileUserUiModel) {
        with(viewBinding) {
            Glide.with(requireContext())
                .load(profileData.avatar)
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.error)
                .into(avatarIv)
        }
    }

    private fun setButtons() {
        with(viewBinding) {
            avatarIv.setOnClickListener {
                ImagePicker.with(fragment = this@ProfileFragment)
                    .cropSquare()
                    .compress(512)
                    .maxResultSize(width = 512, height = 512)
                    .createIntent { intent ->
                        imagePickLauncher.launch(intent)
                    }
            }
            changeCredentialsBtn.setOnClickListener {
                viewModel.openChangeCredentialsScreen()
            }
            favouriteEventsBtn.setOnClickListener {
                viewModel.openFavouriteEventsScreen()
            }
            logoutBtn.setOnClickListener {
                viewModel.logout()
            }
        }
    }
}