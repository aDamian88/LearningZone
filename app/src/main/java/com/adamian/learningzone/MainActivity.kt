package com.adamian.learningzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.adamian.learningzone.ui.navigation.NavGraph
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningZoneAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
