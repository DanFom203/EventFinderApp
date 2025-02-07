package com.itis.common.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.itis.common.utils.observe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseComposeFragment<T : BaseViewModel> : Fragment() {

    @Inject
    open lateinit var viewModel: T

    private val observables = mutableListOf<SharedFlow<*>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
//        initViews()
//        lifecycleScope.launch{
//            subscribe(viewModel)
//        }
    }

//    abstract fun initViews()

    abstract fun inject()

//    abstract suspend fun subscribe(viewModel: T)

//    inline fun <T> Flow<T>.observe(crossinline block: (T) -> Unit): Job {
//        return observe(fragment = this@BaseComposeFragment, block)
//    }
//
//    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
//        Toast.makeText(requireContext(), message, duration).show()
//    }
}