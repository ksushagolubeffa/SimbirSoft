package com.example.diaryapp.di

import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.diaryapp.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideCalendarRouter(navigator: Navigator): CalendarRouter = navigator
}
