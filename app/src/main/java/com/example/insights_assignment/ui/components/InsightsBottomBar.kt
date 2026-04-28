package com.example.insights_assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.zIndex
import com.example.insights_assignment.R
import com.example.insights_assignment.model.BottomNavItem
import com.example.insights_assignment.ui.theme.DMSansFontFamily
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

private val pillShape = RoundedCornerShape(100)
private const val BAR_HEIGHT_DP = 60
private const val CIRCLE_SIZE_DP = 60

private val navItems = listOf(
    BottomNavItem(iconResId = R.drawable.ic_nav_home, label = "Home", isActive = false),
    BottomNavItem(iconResId = R.drawable.ic_nav_track, label = "Track", isActive = false),
    BottomNavItem(iconResId = R.drawable.ic_nav_insights, label = "Insights", isActive = true)
)

private val glassBorder = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFA19191).copy(alpha = 0.30f),
        Color(0xFFBBBBBB).copy(alpha = 0.25f),
        Color(0xFFD0D0D0).copy(alpha = 0.20f)
    )
)

private val liquidGlassStyle = HazeStyle(
    blurRadius = 25.dp,
    tints = listOf(
        HazeTint(color = Color.White.copy(alpha = 0.55f))
    )
)

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun InsightsBottomBar(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val inactiveColor = Color.Black.copy(alpha = 0.4f)
    val activeColor = Color.Black

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── Main pill ──
        Box(
            modifier = Modifier
                .weight(1f)
                .height(BAR_HEIGHT_DP.dp)
                .shadow(
                    elevation = 32.dp,
                    shape = pillShape,
                    clip = false,
                    ambientColor = Color(0x26000000),
                    spotColor = Color(0x2A000000)
                )
                .clip(pillShape)
                .hazeEffect(state = hazeState, style = liquidGlassStyle)
                .background(Color.White.copy(alpha = 0.20f))
                .border(width = 0.75.dp, brush = glassBorder, shape = pillShape),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 25.dp, end = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEach { item ->
                    val tint = if (item.isActive) activeColor else inactiveColor
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label,
                            tint = tint,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            item.label,
                            fontSize = 11.sp,
                            fontFamily = DMSansFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = tint
                        )
                    }
                }
            }
        }

        // ── Circle button ──
        Box(
            modifier = Modifier
                .size(CIRCLE_SIZE_DP.dp)
                .shadow(
                    elevation = 32.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = Color(0x26000000),
                    spotColor = Color(0x2A000000)
                )
                .clip(CircleShape)
                .hazeEffect(state = hazeState, style = liquidGlassStyle)
                .background(Color.White.copy(alpha = 0.20f))
                .border(width = 0.75.dp, brush = glassBorder, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_add),
                contentDescription = "Add",
                tint = inactiveColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
