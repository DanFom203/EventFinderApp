package com.itis.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.itis.common.R
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.itis.common.utils.setBarColorBackground

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    @Inject
    open lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource())
        setBarColorBackground(R.color.lightYellow)
        inject()
        initViews()
        lifecycleScope.launch {
            subscribe(viewModel)
        }
    }

    abstract fun inject()

    abstract fun layoutResource(): Int

    abstract fun initViews()

    abstract suspend fun subscribe(viewModel: T)
}