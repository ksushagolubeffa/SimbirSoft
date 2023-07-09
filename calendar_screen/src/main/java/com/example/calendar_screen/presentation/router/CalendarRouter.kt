package com.example.calendar_screen.presentation.router

import android.content.Context
import android.os.Bundle
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel

interface CalendarRouter {

    fun openStart(context: Context)

    fun openCalendarFragment()

    fun openDetailFragment(bundle: Bundle)
}
