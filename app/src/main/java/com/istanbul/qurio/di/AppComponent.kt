package com.istanbul.qurio.di

import android.app.Application
import com.istanbul.qurio.ui.home.HomeFragment
import com.istanbul.qurio.ui.home.SeeAllGamesFragment
import com.istanbul.qurio.ui.home.SeeAllLastGamesFragment
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
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SeeAllGamesFragment)
    fun inject(fragment: SeeAllLastGamesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}