package com.example.insights_assignment.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insights_assignment.R
import com.example.insights_assignment.ui.theme.DMSansFontFamily
import com.example.insights_assignment.ui.theme.TextPrimary

@Composable
fun InsightsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_header_grid),
            contentDescription = "Menu",
            modifier = Modifier.size(28.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Insights",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = DMSansFontFamily,
            color = TextPrimary,
            letterSpacing = (-0.32).sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(24.dp))
    }
}
