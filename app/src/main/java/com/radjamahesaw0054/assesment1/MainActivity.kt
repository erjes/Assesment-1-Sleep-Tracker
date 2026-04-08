package com.radjamahesaw0054.assesment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.radjamahesaw0054.assesment1.data.local.dao.SleepDao
import com.radjamahesaw0054.assesment1.data.local.database.SleepDatabase
import com.radjamahesaw0054.assesment1.repository.SleepRepository
import com.radjamahesaw0054.assesment1.ui.navigation.NavGraph
import com.radjamahesaw0054.assesment1.ui.theme.Assesment1Theme
import com.radjamahesaw0054.assesment1.viewmodel.SleepViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesment1Theme {
                NavGraph(
                    navController = rememberNavController(),
                    viewModel = viewModel(
                        factory = SleepViewModelFactory(
                            SleepRepository(
                                SleepDao(
                                    SleepDatabase(
                                        this)
                                )
                            )
                        )
                    )
                )
            }
        }
    }
}