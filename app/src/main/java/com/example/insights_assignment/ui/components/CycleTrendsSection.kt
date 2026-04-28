package com.example.insights_assignment.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.R
import com.example.insights_assignment.ui.theme.*

@Composable
fun CycleTrendsSection() {
    SectionTitle("Cycle Trends")
    InsightsCard { CycleTrendsChart() }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun CycleTrendsChart() {
    val textMeasurer = rememberTextMeasurer()
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
    val cycleDays = listOf(28, 30, 28, 32, 28, 28)
    val designWidth = 343.dp; val designHeight = 237.dp

    BoxWithConstraints(Modifier.fillMaxWidth().aspectRatio(designWidth.value / designHeight.value)) {
        val layoutScale = maxWidth.value / designWidth.value

        Canvas(Modifier.fillMaxSize()) {
            val scaleX = size.width / designWidth.toPx()
            val scaleY = size.height / designHeight.toPx()
            val scale = minOf(scaleX, scaleY)
            fun x(v: Float) = v.dp.toPx() * scaleX
            fun y(v: Float) = v.dp.toPx() * scaleY
            fun s(v: Float) = v.dp.toPx() * scale

            val gridLeft = x(32f); val gridRight = size.width - x(31f)
            val gridTop = y(44f); val gridMid = y(121.5f); val gridBottom = y(199f)
            val gridColor = Color(0x99E0DDDD)

            listOf(gridTop, gridMid, gridBottom).forEach { gy ->
                drawLine(gridColor, Offset(gridLeft, gy), Offset(gridRight, gy), s(1f),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(s(3f), s(3f))))
            }

            data class BarSpec(val x: Float, val top: Float, val totalH: Float,
                val greenY: Float, val greenH: Float, val pinkY: Float, val pinkH: Float)

            val barWidth = s(13f); val barRadius = barWidth / 2f
            val specs = listOf(
                BarSpec(x(45f), y(45f), s(153.61f), y(45+39.52f), s(34.09f), y(45+119.52f), s(34.09f)),
                BarSpec(x(94f), y(33f), s(166f), y(33+21.52f), s(34.09f), y(33+111.52f), s(34.09f)),
                BarSpec(x(141f), y(44f), s(155f), y(44+20.52f), s(34.09f), y(44+100.52f), s(34.09f)),
                BarSpec(x(191f), y(33f), s(166f), y(33+21.52f), s(34.09f), y(33+111.52f), s(34.09f)),
                BarSpec(x(240f), y(44f), s(155f), y(44+20.52f), s(34.09f), y(44+100.52f), s(34.09f)),
                BarSpec(x(290f), y(45f), s(153.61f), y(45+39.52f), s(34.09f), y(45+119.52f), s(34.09f))
            )

            specs.forEachIndexed { idx, spec ->
                val barX = spec.x; val barCenterX = barX + barWidth / 2f
                drawRoundRect(CyclePurple, Offset(barX, spec.top), Size(barWidth, spec.totalH), CornerRadius(barRadius))
                drawRoundRect(CyclePink, Offset(barX, spec.pinkY), Size(barWidth, spec.pinkH), CornerRadius(barRadius))
                drawRoundRect(CycleGreen, Offset(barX, spec.greenY), Size(barWidth, spec.greenH), CornerRadius(barRadius))

                val valueStyle = TextStyle(fontSize = (13f * scale).coerceIn(11f, 15f).sp,
                    fontFamily = DMSansFontFamily, fontWeight = FontWeight.Bold, color = TextPrimary)
                val valueMeasure = textMeasurer.measure(cycleDays[idx].toString(), valueStyle)
                drawText(textMeasurer, cycleDays[idx].toString(),
                    topLeft = Offset(barCenterX - valueMeasure.size.width / 2f, spec.top - valueMeasure.size.height - s(4f)),
                    style = valueStyle)

                val monthStyle = TextStyle(fontSize = (11f * scale).coerceIn(9f, 13f).sp,
                    fontFamily = DMSansFontFamily, fontWeight = FontWeight.Normal, color = TextPrimary)
                val monthMeasure = textMeasurer.measure(months[idx], monthStyle)
                drawText(textMeasurer, months[idx],
                    topLeft = Offset(barCenterX - monthMeasure.size.width / 2f, y(211f)), style = monthStyle)
            }
        }

        // Icon overlays
        fun scaledDp(value: Float) = (value * layoutScale).dp

        listOf(48f to 98f, 97f to 68f, 144f to 78f, 194f to 68f, 243f to 78f, 293f to 98f).forEach { (px, py) ->
            Icon(painterResource(R.drawable.ic_sun), null, tint = Color.Unspecified,
                modifier = Modifier.size(scaledDp(8f)).absoluteOffset(x = scaledDp(px), y = scaledDp(py)))
        }
        listOf(48f to 187.5f, 97f to 167.5f, 144f to 167.5f, 194f to 167.5f, 243f to 167.5f, 293f to 187.5f).forEach { (px, py) ->
            Icon(painterResource(R.drawable.ic_droplet), null, tint = Color.Unspecified,
                modifier = Modifier.size(scaledDp(7f)).absoluteOffset(x = scaledDp(px), y = scaledDp(py)))
        }
        Icon(painterResource(R.drawable.ic_arrow_left), "Previous", tint = Color.Unspecified,
            modifier = Modifier.size(scaledDp(20f)).absoluteOffset(x = scaledDp(10f), y = scaledDp(110f)))
        Icon(painterResource(R.drawable.ic_arrow_right), "Next", tint = Color.Unspecified,
            modifier = Modifier.size(scaledDp(20f)).absoluteOffset(x = scaledDp(315f), y = scaledDp(110f)))
    }
}
