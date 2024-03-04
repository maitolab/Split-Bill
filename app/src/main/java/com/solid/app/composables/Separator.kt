package com.solid.app.composables

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors

@Composable
fun Separator(thickness: Dp = 1.dp, color: Color = themeColors().divider.Normal) {
    Divider(
        thickness = thickness,
        color = color
    )
}