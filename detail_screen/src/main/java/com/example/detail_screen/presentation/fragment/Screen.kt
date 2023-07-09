package com.example.detail_screen.presentation.fragment

sealed class Screen(val route: String) {
    object Detail: Screen(route = "back")
}