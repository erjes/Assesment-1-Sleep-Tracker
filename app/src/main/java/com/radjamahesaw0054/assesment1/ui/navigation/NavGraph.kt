package com.radjamahesaw0054.assesment1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radjamahesaw0054.assesment1.ui.screens.history.HistoryScreen
import com.radjamahesaw0054.assesment1.ui.screens.input.InputScreen
import com.radjamahesaw0054.assesment1.viewmodel.SleepViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: SleepViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Input.route
    ) {
        composable(Screen.Input.route) {
            InputScreen(
                viewModel = viewModel,
                onNavigateToHistory = { navController.navigate(Screen.History.route) }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}