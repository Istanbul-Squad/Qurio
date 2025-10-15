package com.istanbul.qurio.di

import com.istanbul.qurio.ui.main.MainActivity
import com.istanbul.qurio.ui.play.PlayFragment
import com.istanbul.qurio.ui.result.ResultFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: PlayFragment)
    fun inject(fragment: ResultFragment)
}