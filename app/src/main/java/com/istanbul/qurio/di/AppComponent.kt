package com.istanbul.qurio.di

import android.app.Application
import com.istanbul.qurio.ui.main.MainActivity
import com.istanbul.qurio.ui.play.PlayFragment
import com.istanbul.qurio.ui.result.ResultFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: PlayFragment)
    fun inject(fragment: ResultFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}