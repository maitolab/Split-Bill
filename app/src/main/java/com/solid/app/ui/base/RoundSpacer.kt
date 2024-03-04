package com.solid.app.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors

@Composable
fun RoundSpacer(modifier: Modifier, radius: Dp = 8.dp) {
    Spacer(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(
                color = themeColors().divider.Strong,
                shape = RoundedCornerShape(radius)
            )
    )
}