package com.example.diaryapp

import android.content.Context
import com.example.calendar_screen.presentation.di.CalendarComponent
import com.example.calendar_screen.presentation.di.CalendarModule
import com.example.calendar_screen.presentation.fragments.CalendarFragment
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.database.di.DatabaseModule
import com.example.detail_screen.presentation.di.DetailComponent
import com.example.detail_screen.presentation.fragment.DetailFragment
import com.example.diaryapp.di.AppModule
import com.example.diaryapp.di.NavigationModule
import com.example.diaryapp.di.SubcomponentsModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
        AppModule::class,
        CalendarModule::class,
        DatabaseModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent: MainDependencies  {

    fun injectCalendarRouter(calendarRouter: CalendarRouter)

    fun calendarComponent(): CalendarComponent.Builder

    fun detailComponent(): DetailComponent.Builder

    fun injectDetailFragment(detailFragment: DetailFragment)

    fun injectCalendarFragment(calendarFragment: CalendarFragment)

    fun injectApp(app: App)

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
