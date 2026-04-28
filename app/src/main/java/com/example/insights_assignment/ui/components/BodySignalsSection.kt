package com.example.insights_assignment.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.model.BubbleSpec
import com.example.insights_assignment.model.DonutSegment
import com.example.insights_assignment.ui.theme.*

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BodySignalsSection() {
    SectionTitle("Body Signals")
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .shadow(6.dp, RoundedCornerShape(12.dp), ambientColor = Color(0x140D0A2C), spotColor = Color(0x140D0A2C)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)) {
            Text("Symptom Trends", fontSize = 20.sp, fontFamily = DMSansFontFamily,
                fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.32).sp)
            Spacer(Modifier.height(4.dp))
            Text("Compared to last cycle", fontSize = 16.sp, fontFamily = DMSansFontFamily,
                fontWeight = FontWeight.Normal, color = TextSecondary)
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val chartScale = maxWidth.value / 319f
                Column(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.height((29f * chartScale).dp))
                    DonutChart()
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun DonutChart() {
    val segments = listOf(
        DonutSegment("Bloating", 31, DonutBloating),
        DonutSegment("Fatigue", 21, DonutFatigue),
        DonutSegment("Acne", 17, DonutAcne),
        DonutSegment("Mood", 30, DonutMood)
    )
    val total = segments.sumOf { it.percentage }.toFloat()
    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxWidth().aspectRatio(319f / 288.82f)) {
            val figmaGroupW = 282f; val figmaGroupH = 288.82f; val figmaContentW = 319f
            val groupOffsetX = (18f / figmaContentW) * size.width
            val scaleX = size.width / figmaContentW
            val scaleY = size.height / figmaGroupH
            val scale = minOf(scaleX, scaleY)

            fun figmaToCanvas(fgX: Float, fgY: Float) = Offset(groupOffsetX + fgX * scaleX, fgY * scaleY)

            val ringDiameter = 272.9f
            val ringCenter = figmaToCanvas(ringDiameter / 2f, ringDiameter / 2f)
            val ringRadiusScaled = (ringDiameter / 2f) * scale
            val strokeW = ringRadiusScaled * 0.335f
            val arcRadius = ringRadiusScaled - strokeW / 2f
            val arcSize = Size(arcRadius * 2f, arcRadius * 2f)
            val arcTopLeft = Offset(ringCenter.x - arcRadius, ringCenter.y - arcRadius)

            fun segmentBrush(seg: DonutSegment): Brush = when (seg.label) {
                "Bloating" -> Brush.linearGradient(listOf(Color(0xFFB4A8DA), Color(0xFFF5F2FF)),
                    start = Offset(ringCenter.x + ringRadiusScaled * 0.35f, ringCenter.y - ringRadiusScaled),
                    end = Offset(ringCenter.x + ringRadiusScaled, ringCenter.y + ringRadiusScaled * 0.48f))
                "Fatigue" -> Brush.linearGradient(listOf(Color(0xFFE99597), Color(0xFFFFE4E4)),
                    start = Offset(ringCenter.x + ringRadiusScaled, ringCenter.y + ringRadiusScaled * 0.20f),
                    end = Offset(ringCenter.x - ringRadiusScaled * 0.12f, ringCenter.y + ringRadiusScaled))
                "Acne" -> Brush.linearGradient(listOf(Color(0xFFECFFF9), Color(0xFF6E8C82)),
                    start = Offset(ringCenter.x - ringRadiusScaled, ringCenter.y + ringRadiusScaled * 0.06f),
                    end = Offset(ringCenter.x - ringRadiusScaled * 0.08f, ringCenter.y + ringRadiusScaled))
                else -> Brush.linearGradient(listOf(Color(0xFFFFF1F1), Color(0xFFF4C3C4)),
                    start = Offset(ringCenter.x - ringRadiusScaled * 0.16f, ringCenter.y - ringRadiusScaled),
                    end = Offset(ringCenter.x - ringRadiusScaled, ringCenter.y + ringRadiusScaled * 0.14f))
            }

            var startAngle = -90f
            segments.forEach { seg ->
                val sweep = (seg.percentage / total) * 360f
                drawArc(segmentBrush(seg), startAngle, sweep, false, arcTopLeft, arcSize,
                    style = Stroke(width = strokeW, cap = StrokeCap.Butt))
                startAngle += sweep
            }

            val bubbleRadiusFigma = 30.135f; val bubbleRadiusScaled = bubbleRadiusFigma * scale
            val bubbles = listOf(
                BubbleSpec(DonutSegment("Mood", 30, DonutMood), 36.955f, 57.425f),
                BubbleSpec(DonutSegment("Bloating", 31, DonutBloating), 251.865f, 71.065f),
                BubbleSpec(DonutSegment("Fatigue", 21, DonutFatigue), 193.875f, 258.685f),
                BubbleSpec(DonutSegment("Acne", 17, DonutAcne), 30.135f, 221.165f)
            )
            val percentFont = (15f * scale).coerceIn(11f, 17f).sp
            val labelFont = (13f * scale).coerceIn(10f, 15f).sp

            bubbles.forEach { bubble ->
                val bubbleCenter = figmaToCanvas(bubble.centerX, bubble.centerY)
                val shadowOffset = Offset(0f, 4.46f * scale)
                val softShadowCenter = bubbleCenter + shadowOffset
                val softShadowRadius = bubbleRadiusScaled + 21.76f * scale
                drawCircle(Brush.radialGradient(
                    listOf(Color.Black.copy(alpha = 0.18f), Color.Black.copy(alpha = 0.10f),
                        Color.Black.copy(alpha = 0.03f), Color.Transparent),
                    center = softShadowCenter, radius = softShadowRadius),
                    radius = softShadowRadius, center = softShadowCenter)
                drawCircle(Color.White, radius = bubbleRadiusScaled, center = bubbleCenter)

                val pctText = "${bubble.segment.percentage}% "
                val pctStyle = TextStyle(fontSize = percentFont, fontFamily = DMSansFontFamily,
                    fontWeight = FontWeight.Bold, color = Color.Black)
                val pctM = textMeasurer.measure(pctText, pctStyle)
                drawText(textMeasurer, pctText,
                    topLeft = Offset(bubbleCenter.x - pctM.size.width / 2f, bubbleCenter.y - bubbleRadiusScaled * 0.36f),
                    style = pctStyle)

                val lblStyle = TextStyle(fontSize = labelFont, fontFamily = DMSansFontFamily,
                    fontWeight = FontWeight.Medium, color = Color.Black)
                val lblM = textMeasurer.measure(bubble.segment.label, lblStyle)
                drawText(textMeasurer, bubble.segment.label,
                    topLeft = Offset(bubbleCenter.x - lblM.size.width / 2f, bubbleCenter.y + bubbleRadiusScaled * 0.08f),
                    style = lblStyle)
            }
        }
    }
}
