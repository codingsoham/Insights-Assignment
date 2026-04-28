package com.example.insights_assignment.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.insights_assignment.ui.components.BodyMetabolicTrendsSection
import com.example.insights_assignment.ui.components.BodySignalsSection
import com.example.insights_assignment.ui.components.CycleTrendsSection
import com.example.insights_assignment.ui.components.InsightsBottomBar
import com.example.insights_assignment.ui.components.InsightsHeader
import com.example.insights_assignment.ui.components.LifestyleImpactSection
import com.example.insights_assignment.ui.components.StabilitySummarySection
import com.example.insights_assignment.ui.theme.CyclePink
import com.example.insights_assignment.ui.theme.ScreenBackground
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun InsightsScreen() {
    val scrollState = rememberScrollState()
    val hazeState = rememberHazeState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        // Scrollable content — marked as haze source
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
                .verticalScroll(scrollState)
        ) {
            FigmaBackgroundGlow()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                InsightsHeader()
                Spacer(modifier = Modifier.height(12.dp))
                StabilitySummarySection()
                Spacer(modifier = Modifier.height(12.dp))
                CycleTrendsSection()
                Spacer(modifier = Modifier.height(12.dp))
                BodyMetabolicTrendsSection()
                Spacer(modifier = Modifier.height(12.dp))
                BodySignalsSection()
                Spacer(modifier = Modifier.height(12.dp))
                LifestyleImpactSection()
                Spacer(modifier = Modifier.height(24.dp))
                // Extra space so content can scroll behind the bottom bar
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

        // Floating bottom bar — overlays content
        InsightsBottomBar(
            hazeState = hazeState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .zIndex(10f)
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun FigmaBackgroundGlow() {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val frameHeight = maxWidth * (491f / 375f)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(frameHeight)
        ) {
            val scale = size.width / 375.dp.toPx()
            val sourceRadius = 78.dp.toPx() * scale
            val blurRadius = 125.dp.toPx() * scale
            val glowRadius = sourceRadius + blurRadius * 2f
            val center = Offset(
                x = 270.dp.toPx() * scale,
                y = 163.dp.toPx() * scale
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0.00f to CyclePink.copy(alpha = 0.18f),
                        0.24f to CyclePink.copy(alpha = 0.17f),
                        0.56f to CyclePink.copy(alpha = 0.08f),
                        0.82f to CyclePink.copy(alpha = 0.025f),
                        1.00f to Color.Transparent
                    ),
                    center = center,
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = center
            )
        }
    }
}
