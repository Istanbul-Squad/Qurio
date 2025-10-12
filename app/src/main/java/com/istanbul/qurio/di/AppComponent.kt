package com.istanbul.qurio.di

import com.istanbul.qurio.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity) // this will change based on fragment or activity
}