package com.istanbul.qurio.di

import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.repository.TriviaRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindTriviaRepository(
        triviaRepositoryImpl: TriviaRepositoryImpl
    ): TriviaRepository




}