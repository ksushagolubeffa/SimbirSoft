package com.example.detail_screen.presentation.di

import com.example.calendar_screen.presentation.di.CalendarComponent

interface DetailComponentProvider {
    fun provideDetailComponent(): DetailComponent
}