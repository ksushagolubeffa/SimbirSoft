package com.example.diaryapp

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [MainDependencies::class]
)
interface MainComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: AppCompatActivity,
            deps: MainDependencies
        ): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}
