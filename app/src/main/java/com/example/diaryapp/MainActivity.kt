package com.example.diaryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.diaryapp.databinding.ActivityMainBinding
import com.example.diaryapp.di.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var navigator: Navigator
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        App.appComponent.inject(this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigator.initialize(navController)
        navigator.attachNavController(navController, R.navigation.main_nav_graph)
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
