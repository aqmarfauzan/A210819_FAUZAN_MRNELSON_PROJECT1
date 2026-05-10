package com.example.a210819_fauzan_mrnelson_project1  // CHANGED PACKAGE

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a210819_fauzan_mrnelson_project1.ui.theme.SolarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolarTheme {
                val viewModel: SolarViewModel = viewModel()
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}