package com.example.insights_assignment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = com.example.insights_assignment.R.array.com_google_android_gms_fonts_certs
)

val DMSansFont = GoogleFont("DM Sans")

val DMSansFontFamily = FontFamily(
    Font(googleFont = DMSansFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = DMSansFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = DMSansFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = DMSansFont, fontProvider = provider, weight = FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)