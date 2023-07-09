package com.example.diaryapp.di

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.diaryapp.MainActivity
import com.example.diaryapp.R

class Navigator: CalendarRouter {
    private var navController: NavController? = null

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    override fun openStart(context: Context) {
        MainActivity.start(context)
    }

    override fun openCalendarFragment() {
        navController?.navigate(R.id.action_detailFragment_to_calendarFragment)
    }

    override fun openDetailFragment(bundle: Bundle) {
        navController?.navigate(R.id.action_calendarFragment_to_detailFragment, bundle)
    }
}
