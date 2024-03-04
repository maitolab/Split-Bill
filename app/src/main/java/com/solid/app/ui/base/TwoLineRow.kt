package com.solid.app.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun TwoLineRow(modifier: Modifier = Modifier, title: String, description: String) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = UIKitTypography.Body1Medium16,
            color = themeColors().text.Stronger
        )

        Text(
            text = description,
            style = UIKitTypography.Body2Regular14,
            color = themeColors().text.Normal
        )
    }
}