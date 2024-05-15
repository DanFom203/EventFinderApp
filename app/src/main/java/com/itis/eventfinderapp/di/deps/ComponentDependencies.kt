package com.itis.eventfinderapp.di.deps

import android.app.Activity
import com.itis.eventfinderapp.App
import dagger.MapKey
import kotlin.reflect.KClass

interface ComponentDependencies

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

inline fun <reified T : ComponentDependencies> Activity.findComponentDependencies(): T {
    return findComponentDependenciesProvider()[T::class.java] as T
}

fun Activity.findComponentDependenciesProvider(): ComponentDependenciesProvider {
    return (application as? App)?.dependencies
        ?: throw IllegalStateException("Can not find suitable dagger provider for $this")
}