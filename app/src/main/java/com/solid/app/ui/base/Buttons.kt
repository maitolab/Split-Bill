package com.solid.app.ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = themeColors().project.Normal,
        contentColor = themeColors().textInvert.Stronger,
        disabledContainerColor = themeColors().divider.Strong,
        disabledContentColor = themeColors().textInvert.Normal
    ),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding,
        enabled = enabled,
        border = border,
        elevation = elevation,
        colors = colors
    ) {
        Text(
            text = title,
            style = UIKitTypography.Body1Medium16
        )
    }
}

@Composable
fun AppTextButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = themeColors().project.Normal,
            disabledContentColor = themeColors().project.Normal.copy(0.5f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = UIKitTypography.Body1Medium16
        )
    }
}