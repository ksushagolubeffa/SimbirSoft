package com.example.diaryapp.di

import com.example.calendar_screen.presentation.di.CalendarComponent
import com.example.detail_screen.presentation.di.DetailComponent
import dagger.Module

@Module(subcomponents = [CalendarComponent::class, DetailComponent::class])
class SubcomponentsModule {
}
