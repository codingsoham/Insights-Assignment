package com.example.insights_assignment.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.R
import com.example.insights_assignment.model.HeatmapRow
import com.example.insights_assignment.ui.theme.*

private val heatmapRows = listOf(
    HeatmapRow("Sleep", HeatmapSleep, 7),
    HeatmapRow("Hydrate", HeatmapHydrate, 3),
    HeatmapRow("Caffeine", HeatmapCaffeine, 5),
    HeatmapRow("Exercise", HeatmapExercise, 4)
)

private val emptyBrush = Brush.horizontalGradient(listOf(Color(0xFFEDEBEB), Color(0xFFEDEBEB)))

private val rowPalettes = mapOf(
    "Sleep" to listOf(
        Brush.horizontalGradient(listOf(Color(0xFFBAAFDD), Color(0xFFE2DDF0))),
        Brush.horizontalGradient(listOf(Color(0xFFE2DEF1), Color(0xFFB9AEDD))),
        Brush.horizontalGradient(listOf(Color(0xFFB9AEDD), Color(0xFFE2DEF1))),
        Brush.horizontalGradient(listOf(Color(0xFFE1DDF0), Color(0xFFBAAFDD))),
        Brush.horizontalGradient(listOf(Color(0xFFBAAFDD), Color(0xFFE2DDF0))),
        Brush.horizontalGradient(listOf(Color(0xFFE3DEF1), Color(0xFFB9AEDC))),
        Brush.horizontalGradient(listOf(Color(0xFFB9AEDD), Color(0xFFE3DEF1)))
    ),
    "Hydrate" to listOf(
        Brush.horizontalGradient(listOf(Color(0xFFEA9A9C), Color(0xFFF2BEC0))),
        Brush.horizontalGradient(listOf(Color(0xFFF1BFC0), Color(0xFFEA9A9B))),
        Brush.horizontalGradient(listOf(Color(0xFFEA9A9C), Color(0xFFF1BFC0)))
    ),
    "Caffeine" to listOf(
        Brush.horizontalGradient(listOf(Color(0xFF78948A), Color(0xFFC1CECA))),
        Brush.horizontalGradient(listOf(Color(0xFFC2CFCB), Color(0xFF77948A))),
        Brush.horizontalGradient(listOf(Color(0xFF78938A), Color(0xFFC2CFCB))),
        Brush.horizontalGradient(listOf(Color(0xFFC1CECA), Color(0xFF78948B))),
        Brush.horizontalGradient(listOf(Color(0xFF78948B), Color(0xFFC2CECA)))
    ),
    "Exercise" to listOf(
        Brush.horizontalGradient(listOf(Color(0xFFF5C7C8), Color(0xFFF9DCDD))),
        Brush.horizontalGradient(listOf(Color(0xFFF9DDDD), Color(0xFFF5C6C7))),
        Brush.horizontalGradient(listOf(Color(0xFFF6C7C8), Color(0xFFF9DDDD))),
        Brush.horizontalGradient(listOf(Color(0xFFF9DCDD), Color(0xFFF6C7C8)))
    )
)

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LifestyleImpactSection() {
    var monthMenuExpanded by remember { mutableStateOf(false) }
    var selectedMonths by remember { mutableStateOf("4 months") }
    SectionTitle("Lifestyle Impact")
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .shadow(6.dp, RoundedCornerShape(12.dp), ambientColor = Color(0x140D0A2C), spotColor = Color(0x140D0A2C)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(Modifier.padding(start = 12.dp, end = 12.dp, top = 22.dp, bottom = 16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text("Correlation Strength", fontSize = 18.sp, fontFamily = DMSansFontFamily,
                    fontWeight = FontWeight.SemiBold, color = TextPrimary,
                    modifier = Modifier.weight(1f).padding(end = 8.dp))
                Box {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clip(RoundedCornerShape(4.dp))
                            .clickable { monthMenuExpanded = true }
                            .background(InactiveBg)
                            .padding(start = 8.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)) {
                        Text(selectedMonths, fontSize = 12.sp, fontFamily = DMSansFontFamily,
                            fontWeight = FontWeight.Normal, color = TextSecondary, softWrap = false)
                        Spacer(Modifier.width(4.dp))
                        Icon(painterResource(R.drawable.ic_arrow_down), "Dropdown",
                            tint = Color.Unspecified, modifier = Modifier.size(16.dp))
                    }
                    DropdownMenu(
                        expanded = monthMenuExpanded,
                        onDismissRequest = { monthMenuExpanded = false },
                        modifier = Modifier.width(116.dp).background(Color.White),
                        containerColor = Color.White, tonalElevation = 0.dp,
                        shadowElevation = 6.dp, shape = RoundedCornerShape(8.dp)
                    ) {
                        listOf("1 month", "3 months", "4 months", "6 months").forEach { option ->
                            DropdownMenuItem(
                                modifier = Modifier.height(40.dp),
                                text = { Text(option, fontSize = 12.sp, fontFamily = DMSansFontFamily,
                                    color = TextPrimary, softWrap = false) },
                                colors = MenuDefaults.itemColors(textColor = TextPrimary),
                                onClick = { selectedMonths = option; monthMenuExpanded = false }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val contentWidth = maxWidth.value
                val labelWidth = (contentWidth * 0.19f).coerceIn(54f, 72f).dp
                val cellGap = (contentWidth * 0.014f).coerceIn(3f, 6f).dp
                val cellWidth = ((contentWidth - labelWidth.value - (cellGap.value * 8f)) / 9f).coerceAtLeast(12f).dp
                val cellHeight = (cellWidth.value * 0.82f).coerceIn(16f, 24f).dp
                val rowGap = (contentWidth * 0.038f).coerceIn(10f, 16f).dp
                val cellRadius = (cellHeight.value * 0.20f).coerceIn(3f, 5f).dp

                Column(verticalArrangement = Arrangement.spacedBy(rowGap)) {
                    heatmapRows.forEach { row ->
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(row.label, fontSize = 12.sp, fontFamily = DMSansFontFamily,
                                fontWeight = FontWeight.Normal, color = TextPrimary,
                                softWrap = false, modifier = Modifier.width(labelWidth))
                            Row(horizontalArrangement = Arrangement.spacedBy(cellGap)) {
                                repeat(9) { col ->
                                    Box(Modifier.size(width = cellWidth, height = cellHeight)
                                        .clip(RoundedCornerShape(cellRadius))
                                        .background(rowPalettes[row.label]?.getOrNull(col) ?: emptyBrush))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
