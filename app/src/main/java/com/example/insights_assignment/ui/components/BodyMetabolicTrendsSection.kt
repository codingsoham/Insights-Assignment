package com.example.insights_assignment.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.ui.theme.*

@Composable
fun BodyMetabolicTrendsSection() {
    var selectedTab by remember { mutableStateOf("Monthly") }

    SectionTitle("Body & Metabolic Trends")
    InsightsCard {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Your weight", fontSize = 18.sp, fontFamily = DMSansFontFamily,
                    fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text("in kg", fontSize = 14.sp, fontFamily = DMSansFontFamily,
                    fontWeight = FontWeight.Normal, color = TextSecondary)
            }
            Row(Modifier.padding(start = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                listOf("Monthly", "Weekly").forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(Modifier.height(34.dp).width(72.dp).clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color.Black else InactiveBg, RoundedCornerShape(8.dp))
                        .clickable { selectedTab = tab }.padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center) {
                        Text(tab, fontSize = 12.sp, fontFamily = DMSansFontFamily,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal)
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        WeightChartSvgAligned()
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WeightChartSvgAligned() {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May")
    val textMeasurer = rememberTextMeasurer()
    val designWidth = 319.dp; val designHeight = 183.dp
    val contentStartX = 12f; val chartStartY = 67f

    BoxWithConstraints(Modifier.fillMaxWidth().aspectRatio(designWidth.value / designHeight.value)) {
        Canvas(Modifier.fillMaxSize()) {
            val scaleX = size.width / designWidth.toPx()
            val scaleY = size.height / designHeight.toPx()
            val scale = minOf(scaleX, scaleY)
            fun x(v: Float) = v.dp.toPx() * scaleX
            fun y(v: Float) = v.dp.toPx() * scaleY
            fun s(v: Float) = v.dp.toPx() * scale
            fun cardToCanvas(cx: Float, cy: Float) = Offset(x(cx - contentStartX), y(cy - chartStartY))

            val gridColor = Color(0x99E0DDDD)
            listOf(72f, 157f, 228f).forEach { lineY ->
                drawLine(gridColor, cardToCanvas(39f, lineY),
                    cardToCanvas(if (lineY == 228f) 321f else 320.5f, lineY), s(1f),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(s(3f), s(3f))))
            }

            val yLabelStyle = TextStyle(fontSize = (8f * scale).coerceIn(7f, 10f).sp,
                fontFamily = DMSansFontFamily, fontWeight = FontWeight.Normal, color = TextPrimary)
            listOf("75" to 72f, "50" to 157f, "25" to 228f).forEach { (label, cardY) ->
                val measured = textMeasurer.measure(label, yLabelStyle)
                val center = cardToCanvas(39f, cardY)
                drawText(textMeasurer, label,
                    topLeft = Offset(cardToCanvas(33f, cardY).x - measured.size.width, center.y - measured.size.height / 2f),
                    style = yLabelStyle)
            }

            val monthLabelStyle = TextStyle(fontSize = (8f * scale).coerceIn(7f, 10f).sp,
                fontFamily = DMSansFontFamily, fontWeight = FontWeight.Normal, color = TextPrimary)
            val monthLefts = listOf(57.27f, 115.18f, 173.71f, 234.89f, 292.78f)
            months.forEachIndexed { i, label ->
                drawText(textMeasurer, label, topLeft = cardToCanvas(monthLefts[i], 238.66f), style = monthLabelStyle)
            }

            val linePath = Path().apply {
                val p0 = cardToCanvas(38f, 218.393f); moveTo(p0.x, p0.y)
                cubicTo(cardToCanvas(51.6964f, 216.107f), cardToCanvas(58.6522f, 211.603f), cardToCanvas(69.2207f, 196.538f))
                cubicTo(cardToCanvas(76.354f, 186.37f), cardToCanvas(80.1947f, 180.454f), cardToCanvas(90.9394f, 174.109f))
                cubicTo(cardToCanvas(98.8218f, 169.454f), cardToCanvas(103.497f, 169.941f), cardToCanvas(109.943f, 172.959f))
                cubicTo(cardToCanvas(125.329f, 180.16f), cardToCanvas(134.18f, 186.139f), cardToCanvas(150.666f, 198.839f))
                cubicTo(cardToCanvas(159.553f, 205.685f), cardToCanvas(165.422f, 203.954f), cardToCanvas(173.063f, 198.264f))
                cubicTo(cardToCanvas(181.385f, 192.067f), cardToCanvas(186.343f, 180.454f), cardToCanvas(197.497f, 140.752f))
                cubicTo(cardToCanvas(203.807f, 118.29f), cardToCanvas(208.114f, 105.657f), cardToCanvas(220.573f, 84.9656f))
                cubicTo(cardToCanvas(224.193f, 78.9543f), cardToCanvas(233.388f, 80.4549f), cardToCanvas(238.898f, 84.9656f))
                cubicTo(cardToCanvas(257.209f, 99.9549f), cardToCanvas(264.228f, 111.955f), cardToCanvas(276.227f, 132.7f))
                cubicTo(cardToCanvas(279.845f, 138.955f), cardToCanvas(289.366f, 134.24f), cardToCanvas(297.946f, 137.301f))
                cubicTo(cardToCanvas(314.149f, 143.082f), cardToCanvas(315.202f, 145.165f), cardToCanvas(323f, 156.455f))
            }

            val areaPath = Path().apply {
                addPath(linePath)
                lineTo(cardToCanvas(323f, 228.455f).x, cardToCanvas(323f, 228.455f).y)
                lineTo(cardToCanvas(38f, 228.455f).x, cardToCanvas(38f, 228.455f).y); close()
            }
            drawPath(areaPath, Brush.verticalGradient(
                colors = listOf(Color(0xFFD27A88).copy(alpha = 0.46f), Color(0xFFD27A88).copy(alpha = 0f)),
                startY = cardToCanvas(180.5f, 81f).y, endY = cardToCanvas(180.5f, 228.455f).y))
            drawPath(linePath, Color(0xFFE99597), style = Stroke(width = s(1.15f), cap = StrokeCap.Round))

            listOf(62f to 205f, 122f to 178f, 178f to 194f, 242f to 88f, 300f to 137f).forEach { (cx, cy) ->
                val center = cardToCanvas(cx, cy)
                drawCircle(Color(0xFFD27A88).copy(alpha = 0.25f), radius = s(8f), center = center)
                drawCircle(Color.White, radius = s(4f), center = center)
                drawCircle(Color(0xFFE99597), radius = s(2f), center = center)
            }
        }
    }
}

/** Extension to use Offset destructuring with cubicTo */
private fun Path.cubicTo(c1: Offset, c2: Offset, end: Offset) {
    cubicTo(c1.x, c1.y, c2.x, c2.y, end.x, end.y)
}
