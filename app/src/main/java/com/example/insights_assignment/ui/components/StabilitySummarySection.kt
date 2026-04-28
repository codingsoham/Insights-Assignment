package com.example.insights_assignment.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.ui.theme.*

@Composable
fun StabilitySummarySection() {
    SectionTitle("Stability Summary")
    StabilitySummaryCard {
        Text("Based on your recent logs and symptom patterns.",
            Modifier.fillMaxWidth(0.92f), fontSize = 16.sp, fontFamily = DMSansFontFamily,
            fontWeight = FontWeight.Normal, color = TextSecondary, lineHeight = 20.sp)
        Spacer(Modifier.height(28.dp))
        Text("Stability Score", fontSize = 18.sp, fontFamily = DMSansFontFamily,
            fontWeight = FontWeight.Normal, color = TextPrimary, lineHeight = 16.sp)
        Spacer(Modifier.height(4.dp))
        Text("78%", fontSize = 32.sp, fontFamily = DMSansFontFamily,
            fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 18.sp)
        Spacer(Modifier.height(5.dp))
        StabilityChart()
    }
}

@Composable
private fun StabilitySummaryCard(content: @Composable ColumnScope.() -> Unit) {
    val cardShape = RoundedCornerShape(12.dp)
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .shadow(4.dp, cardShape, ambientColor = Color(0x140D0A2C), spotColor = Color(0x140D0A2C)),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(Modifier.fillMaxWidth().clip(cardShape).background(Color.White)) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val sourceRadius = 50.dp.toPx()
                val glowRadius = sourceRadius + 85.dp.toPx() * 2f
                val center = Offset(size.width - sourceRadius, sourceRadius)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(StabilityGreen.copy(alpha = 0.20f), StabilityGreen.copy(alpha = 0.14f),
                            StabilityGreen.copy(alpha = 0.06f), Color.Transparent),
                        center = center, radius = glowRadius),
                    radius = glowRadius, center = center)
            }
            Column(Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp), content = content)
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun StabilityChart() {
    val months = listOf("Jan", "Feb", "Mar", "Apr")
    val currentIdx = 2
    val textMeasurer = rememberTextMeasurer()
    val designWidth = 319.dp; val designHeight = 190.dp

    BoxWithConstraints(Modifier.fillMaxWidth().aspectRatio(designWidth.value / designHeight.value)) {
        Canvas(Modifier.fillMaxSize()) {
            val scaleX = size.width / designWidth.toPx()
            val scaleY = size.height / designHeight.toPx()
            val scale = minOf(scaleX, scaleY)
            fun x(v: Float) = v.dp.toPx() * scaleX
            fun y(v: Float) = v.dp.toPx() * scaleY
            fun s(v: Float) = v.dp.toPx() * scale

            val tickLeft = x(52f); val tickRight = size.width - x(26f)
            val plotLeft = x(44f); val plotRight = size.width - x(1f)
            val topPad = y(56f); val bottomPad = y(34f)
            val chartW = tickRight - tickLeft; val bandW = plotRight - plotLeft
            val chartH = size.height - topPad - bottomPad
            val minY = 23.6f; val maxY = 33.2f

            fun yPos(v: Float) = topPad + chartH * (1f - (v - minY) / (maxY - minY))
            fun xPos(idx: Int) = tickLeft + chartW * idx.toFloat() / (months.size - 1).coerceAtLeast(1)

            val svgBandWidth = 292.011f; val svgMaskRight = 187.08f
            val svgMaskTop = 39.1699f; val svgMaskBottom = 81.2733f
            val marX = plotLeft + bandW * (svgMaskRight / svgBandWidth)
            val baseY = yPos(23.9f)

            val outerPreColor = Color(0xFFD8CCFE); val innerPreColor = Color(0xFFBCA7FD)
            val outerPostColor = Color(0xFFE6E0FB); val innerPostColor = Color(0xFFB4A8DA)
            val bandTop = yPos(32.1f); val bandHeight = baseY - bandTop
            val svgBandBaseY = 81.2727f
            fun svgX(v: Float) = plotLeft + bandW * v / svgBandWidth
            fun svgY(v: Float) = bandTop + bandHeight * v / svgBandBaseY

            val outerBandPath = Path().apply {
                moveTo(svgX(291.696f), svgY(0f))
                cubicTo(svgX(180.946f), svgY(51.5295f), svgX(51.0863f), svgY(75.6358f), svgX(0.0000152588f), svgY(81.2477f))
                lineTo(svgX(291.696f), svgY(81.2726f)); lineTo(svgX(291.696f), svgY(0f)); close()
            }
            val innerBandPath = Path().apply {
                moveTo(svgX(292.011f), svgY(32.285f))
                cubicTo(svgX(211.555f), svgY(61.1353f), svgX(63.9878f), svgY(76.9644f), svgX(0.26112f), svgY(81.2727f))
                cubicTo(svgX(151.847f), svgY(72.2417f), svgX(257.922f), svgY(63.398f), svgX(292.011f), svgY(60.105f))
                lineTo(svgX(292.011f), svgY(32.285f)); close()
            }

            drawPath(outerBandPath, outerPostColor); drawPath(innerBandPath, innerPostColor)
            clipRect(left = svgX(0f), top = svgY(svgMaskTop), right = svgX(svgMaskRight), bottom = svgY(svgMaskBottom)) {
                drawPath(outerBandPath, outerPreColor); drawPath(innerBandPath, innerPreColor)
            }

            val yGridValues = listOf(32f, 28f, 24f); val yGridLabels = listOf("32d", "28d", "24d")
            yGridValues.forEachIndexed { i, v ->
                val gridY = yPos(v); val lbl = yGridLabels[i]
                val labelStyle = TextStyle(fontSize = (12f * scale).coerceIn(10f, 13f).sp, fontFamily = DMSansFontFamily,
                    color = TextPrimary, fontWeight = FontWeight.Normal, lineHeight = (13f * scale).coerceIn(11f, 14f).sp)
                val measured = textMeasurer.measure(lbl, labelStyle)
                drawText(textMeasurer, lbl, topLeft = Offset(0f, gridY - measured.size.height / 2f), style = labelStyle)
                drawLine(Color(0xFFE8E8E8).copy(alpha = 0.13f), Offset(plotLeft, gridY), Offset(plotRight, gridY), s(1f))
            }

            months.forEachIndexed { i, label ->
                val isCurrent = i == currentIdx
                val monthX = if (isCurrent) marX else xPos(i)
                val style = TextStyle(fontSize = (13f * scale).coerceIn(11f, 15f).sp, fontFamily = DMSansFontFamily,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCurrent) TextPrimary else TextSecondary,
                    lineHeight = (14f * scale).coerceIn(12f, 16f).sp)
                val measured = textMeasurer.measure(label, style)
                drawText(textMeasurer, label, topLeft = Offset(monthX - measured.size.width / 2f, size.height - bottomPad + s(10f)), style = style)
            }

            val cx = marX; val currentCenterY = yPos(30f)
            drawLine(color = StabilityGreen.copy(alpha = 0.78f), start = Offset(cx, currentCenterY + s(12f)),
                end = Offset(cx, baseY), strokeWidth = s(2f), pathEffect = PathEffect.dashPathEffect(floatArrayOf(s(6f), s(5f))))
            drawCircle(StabilityGreen, radius = s(6f), center = Offset(cx, currentCenterY))

            val tooltipW = s(80f); val tooltipH = s(43f)
            val tooltipX = cx - tooltipW / 2f; val tooltipY = currentCenterY - tooltipH - s(22f)
            val clampedX = tooltipX.coerceIn(0f, size.width - tooltipW)
            val pointerCenterX = cx.coerceIn(clampedX + s(10f), clampedX + tooltipW - s(10f))

            drawRoundRect(Color.Black, Offset(clampedX, tooltipY), Size(tooltipW, tooltipH), CornerRadius(s(8f), s(8f)))
            val triPath = Path().apply {
                moveTo(pointerCenterX - s(8f), tooltipY + tooltipH)
                lineTo(pointerCenterX, tooltipY + tooltipH + s(9f))
                lineTo(pointerCenterX + s(8f), tooltipY + tooltipH); close()
            }
            drawPath(triPath, Color.Black)

            val ts = TextStyle(fontSize = (12f * scale).coerceIn(10f, 13f).sp, fontFamily = DMSansFontFamily,
                fontWeight = FontWeight.Normal, color = Color.White, lineHeight = (17f * scale).coerceIn(14f, 18f).sp)
            val m1 = textMeasurer.measure("Stability", ts); val m2 = textMeasurer.measure("Improving", ts)
            drawText(textMeasurer, "Stability", topLeft = Offset(clampedX + (tooltipW - m1.size.width) / 2f, tooltipY + s(6f)), style = ts)
            drawText(textMeasurer, "Improving", topLeft = Offset(clampedX + (tooltipW - m2.size.width) / 2f, tooltipY + s(23f)), style = ts)
        }
    }
}
