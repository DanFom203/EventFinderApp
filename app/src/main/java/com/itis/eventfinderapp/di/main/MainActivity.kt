package com.itis.eventfinderapp.di.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.common.base.BaseActivity
import com.itis.common.utils.AsyncResult
import com.itis.eventfinderapp.R
import com.itis.eventfinderapp.databinding.ActivityMainBinding
import com.itis.eventfinderapp.di.deps.findComponentDependencies
import com.itis.eventfinderapp.di.main.di.MainComponent
import com.itis.eventfinderapp.navigation.Navigator
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>(){

    @Inject lateinit var navigator: Navigator

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private var controller: NavController? = null

    override fun inject() {
        MainComponent.init(this, findComponentDependencies())
            .inject(this)
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        controller =
            (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
                .navController
        navigator.attachNavController(this.controller!!, R.navigation.nav_graph)
        findViewById<BottomNavigationView>(R.id.menu_bnv).apply {
            setupWithNavController(this@MainActivity.controller!!)
        }
        viewBinding.menuBnv.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, this.controller!!)
            return@setOnItemSelectedListener true
        }

    }

    override suspend fun subscribe(viewModel: MainViewModel) {
        with(viewBinding) {
            viewModel.sharedPreferencesFlow.collect { result ->
                when(result) {
                    is AsyncResult.Success -> {
                        if (result.getDataOrNull()!!) {
                            menuBnv.visibility = View.VISIBLE
                        } else {
                            menuBnv.visibility = View.INVISIBLE
                        }
                    }
                    is AsyncResult.Error -> {
                        viewModel.errorHandling(result.getExceptionOrNull()!!)
                    }
                    else -> {

                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller?.let {
            navigator.detachNavController(it)
        }
    }

}