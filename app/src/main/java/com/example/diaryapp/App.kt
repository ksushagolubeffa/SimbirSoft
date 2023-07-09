package com.example.diaryapp

import android.app.Application
import com.example.calendar_screen.presentation.di.CalendarComponent
import com.example.calendar_screen.presentation.di.CalendarComponentProvider
import com.example.detail_screen.presentation.di.DetailComponent
import com.example.detail_screen.presentation.di.DetailComponentProvider

class App : Application(), CalendarComponentProvider, DetailComponentProvider {

    override fun onCreate() {
        appComponent = DaggerAppComponent
            .builder()
            .context(applicationContext)
            .build()
        super.onCreate()

    }

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun provideCalendarComponent(): CalendarComponent {
        return appComponent.calendarComponent().build()
    }

    override fun provideDetailComponent(): DetailComponent {
        return appComponent.detailComponent().build()
    }
}
