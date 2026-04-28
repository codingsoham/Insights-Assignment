package com.example.insights_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.insights_assignment.ui.screens.InsightsScreen
import com.example.insights_assignment.ui.theme.InsightsAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsightsAssignmentTheme {
                InsightsScreen()
            }
        }
    }
}