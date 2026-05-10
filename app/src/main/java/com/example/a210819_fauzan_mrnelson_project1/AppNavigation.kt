package com.example.a210819_fauzan_mrnelson_project1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(viewModel: SolarViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // Screen 1: Login
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Screen 2: Home
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Screen 3: Stats
        composable("stats") {
            StatsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Screen 4: Add Energy Log (Form Input)
        composable("add_log") {
            AddLogScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Screen 5: Log List (Summary List)
        composable("log_list") {
            LogListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Screen 6: Settings
        composable("settings") {
            SettingsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}