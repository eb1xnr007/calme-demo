package com.eb1xnr007.calme.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.noto_sans_thai
import org.jetbrains.compose.resources.Font

object CalMeColors {
    val Background = Color(0xFFF6F8F7)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceSoft = Color(0xFFEEF5F1)
    val Ink = Color(0xFF17251F)
    val Muted = Color(0xFF68756E)
    val Line = Color(0xFFDDE6E1)
    val Primary = Color(0xFF1D8A63)
    val PrimaryDark = Color(0xFF126447)
    val PrimarySoft = Color(0xFFDFF5EA)
    val Protein = Color(0xFF7257C9)
    val ProteinSoft = Color(0xFFEEE9FF)
    val Warning = Color(0xFFCE761E)
    val WarningSoft = Color(0xFFFFF1DF)
    val Danger = Color(0xFFC74B48)
}

@Composable
private fun thaiFontFamily() = FontFamily(
    Font(Res.font.noto_sans_thai, FontWeight.Normal),
    Font(Res.font.noto_sans_thai, FontWeight.Medium),
    Font(Res.font.noto_sans_thai, FontWeight.SemiBold),
    Font(Res.font.noto_sans_thai, FontWeight.Bold)
)

private fun calMeTypography(thai: FontFamily) = Typography(
    displayLarge = TextStyle(fontFamily = thai, fontWeight = FontWeight.Bold, fontSize = 30.sp),
    headlineLarge = TextStyle(fontFamily = thai, fontWeight = FontWeight.Bold, fontSize = 26.sp),
    titleLarge = TextStyle(fontFamily = thai, fontWeight = FontWeight.Bold, fontSize = 21.sp),
    titleMedium = TextStyle(fontFamily = thai, fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
    bodyLarge = TextStyle(fontFamily = thai, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = thai, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = thai, fontWeight = FontWeight.Bold, fontSize = 14.sp)
)

private val CalMeColorScheme = lightColorScheme(
    primary = CalMeColors.Primary,
    onPrimary = Color.White,
    primaryContainer = CalMeColors.PrimarySoft,
    onPrimaryContainer = CalMeColors.PrimaryDark,
    secondary = CalMeColors.Protein,
    secondaryContainer = CalMeColors.ProteinSoft,
    error = CalMeColors.Danger,
    background = CalMeColors.Background,
    onBackground = CalMeColors.Ink,
    surface = CalMeColors.Surface,
    onSurface = CalMeColors.Ink,
    surfaceVariant = CalMeColors.SurfaceSoft,
    outline = CalMeColors.Line
)

@Composable
fun CalMeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CalMeColorScheme,
        typography = calMeTypography(thaiFontFamily()),
        content = content
    )
}
