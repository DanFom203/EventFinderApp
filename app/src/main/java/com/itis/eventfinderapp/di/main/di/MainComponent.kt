package com.itis.eventfinderapp.di.main.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.itis.common.di.scope.ScreenScope
import com.itis.eventfinderapp.di.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [
        MainDependencies::class
    ],
    modules = [
        MainModule::class
    ]
)
@ScreenScope
interface MainComponent : MainApi {

    companion object {

        fun init(activity: AppCompatActivity, deps: MainDependencies): MainComponent {
            return DaggerMainComponent.factory().create(
                activity,
                activity,
                deps
            )
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context,
            @BindsInstance
            activity: AppCompatActivity,
            deps: MainDependencies
        ): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}