package com.example.calendar_screen.presentation.di

import com.example.calendar_screen.presentation.fragments.CalendarFragment
import dagger.Subcomponent

@Subcomponent(modules = [CalendarModule::class])
interface CalendarComponent {

    fun injectCalendarFragment(calendarFragment: CalendarFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CalendarComponent
    }
}