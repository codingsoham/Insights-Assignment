package com.example.insights_assignment.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * A single segment of the Body Signals donut chart.
 *
 * @param label   Display label (e.g. "Bloating", "Fatigue")
 * @param percentage  Whole-number percentage this segment represents
 * @param color   The base color for this segment
 */
data class DonutSegment(
    val label: String,
    val percentage: Int,
    val color: Color
)

/**
 * A single row in the Lifestyle Impact correlation heatmap.
 *
 * @param label       Row label (e.g. "Sleep", "Hydrate")
 * @param color       The base color for filled cells
 * @param filledCount How many of the 9 cells are filled
 */
data class HeatmapRow(
    val label: String,
    val color: Color,
    val filledCount: Int
)

/**
 * A navigation item in the bottom bar.
 *
 * @param iconResId   Drawable resource ID for the icon
 * @param label       Display label
 * @param isActive    Whether this item is currently selected
 */
data class BottomNavItem(
    val iconResId: Int,
    val label: String,
    val isActive: Boolean
)

/**
 * Bubble overlay positioned on the donut chart.
 *
 * @param segment  The donut segment this bubble represents
 * @param centerX  X position relative to the chart group origin (Figma coords)
 * @param centerY  Y position relative to the chart group origin (Figma coords)
 */
data class BubbleSpec(
    val segment: DonutSegment,
    val centerX: Float,
    val centerY: Float
)

/**
 * A single heatmap row's cell gradient palette.
 */
data class HeatmapPalette(
    val label: String,
    val filledBrushes: List<Brush>
)
