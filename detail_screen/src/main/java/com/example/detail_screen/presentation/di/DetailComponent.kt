package com.example.detail_screen.presentation.di

import com.example.calendar_screen.presentation.di.CalendarModule
import com.example.calendar_screen.presentation.fragments.CalendarFragment
import com.example.detail_screen.presentation.fragment.DetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [CalendarModule::class])
interface DetailComponent {

    fun injectDetailFragment(detailFragment: DetailFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): DetailComponent
    }
}