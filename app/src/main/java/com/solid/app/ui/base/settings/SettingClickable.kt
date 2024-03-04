package com.solid.app.ui.base.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun SettingClickable(
    modifier: Modifier,
    icon: ImageVector? = null,
    title: String,
    summary: String? = null,
    onClick: () -> Unit
) {
    SettingItem(
        modifier = modifier,
        title = title,
        summary = summary,
        icon = icon,
        onClick = onClick,
        widget = {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = themeColors().text.Normal
            )
        })

}