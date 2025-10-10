package com.istanbul.qurio.di

import com.istanbul.qurio.service.TriviaService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTriviaService(retrofit: Retrofit): TriviaService {
        return retrofit.create(TriviaService::class.java)
    }

    private companion object {
        const val BASE_URL = "https://opentdb.com/"
    }
}