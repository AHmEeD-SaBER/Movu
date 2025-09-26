package com.example.core_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.core_ui.R

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

object AppTypography {

    @Composable
    private fun getScalingFactor(): Float {
        val screenWidthDp = LocalConfiguration.current.screenWidthDp
        return when {
            screenWidthDp >= 600 -> 1.2f  // Tablets
            screenWidthDp >= 411 -> 1.0f  // Large phones
            else -> 0.8f                  // Compact phones
        }
    }

    @Composable
    private fun scaledTextStyle(baseFontSize: Float, fontWeight: FontWeight): TextStyle {
        val scalingFactor = getScalingFactor()
        return TextStyle(
            fontFamily = PoppinsFontFamily,
            fontWeight = fontWeight,
            fontSize = (baseFontSize * scalingFactor).sp
        )
    }

    // Headers
    val h1: TextStyle @Composable get() = scaledTextStyle(48f, FontWeight.Bold)
    val h2: TextStyle @Composable get() = scaledTextStyle(36f, FontWeight.Bold)
    val h3: TextStyle @Composable get() = scaledTextStyle(32f, FontWeight.Bold)
    val h4: TextStyle @Composable get() = scaledTextStyle(32f, FontWeight.SemiBold)
    val h5: TextStyle @Composable get() = scaledTextStyle(28f, FontWeight.Bold)
    val h6: TextStyle @Composable get() = scaledTextStyle(28f, FontWeight.SemiBold)
    val h7: TextStyle @Composable get() = scaledTextStyle(24f, FontWeight.Bold)

    // Subheaders
    val sh1: TextStyle @Composable get() = scaledTextStyle(24f, FontWeight.SemiBold)
    val sh2: TextStyle @Composable get() = scaledTextStyle(24f, FontWeight.Medium)
    val sh3: TextStyle @Composable get() = scaledTextStyle(22f, FontWeight.Bold)
    val sh4: TextStyle @Composable get() = scaledTextStyle(22f, FontWeight.SemiBold)
    val sh5: TextStyle @Composable get() = scaledTextStyle(22f, FontWeight.Medium)
    val sh6: TextStyle @Composable get() = scaledTextStyle(20f, FontWeight.SemiBold)
    val sh7: TextStyle @Composable get() = scaledTextStyle(20f, FontWeight.Medium)
    val sh8: TextStyle @Composable get() = scaledTextStyle(18f, FontWeight.SemiBold)

    // Body Text
    val bt1: TextStyle @Composable get() = scaledTextStyle(18f, FontWeight.Medium)
    val bt2: TextStyle @Composable get() = scaledTextStyle(18f, FontWeight.Normal)
    val bt3: TextStyle @Composable get() = scaledTextStyle(14f, FontWeight.Medium)
    val bt4: TextStyle @Composable get() = scaledTextStyle(14f, FontWeight.Normal)
    val bt5: TextStyle @Composable get() = scaledTextStyle(12f, FontWeight.Medium)
    val bt6: TextStyle @Composable get() = scaledTextStyle(12f, FontWeight.Normal)
    val bt7: TextStyle @Composable get() = scaledTextStyle(10f, FontWeight.Medium)
    val bt8: TextStyle @Composable get() = scaledTextStyle(10f, FontWeight.Normal)
    val bt9: TextStyle @Composable get() = scaledTextStyle(8f, FontWeight.Light)
}
