package com.example.diaryapp.di

import com.example.diaryapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideApp(): App {
        return App()
    }
}
