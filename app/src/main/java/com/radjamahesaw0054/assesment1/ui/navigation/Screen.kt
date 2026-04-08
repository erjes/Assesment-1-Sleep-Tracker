package com.radjamahesaw0054.assesment1.ui.navigation

sealed class Screen(val route: String) {
    object Input : Screen("input_screen")
    object History : Screen("history_screen")
}