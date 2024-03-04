package com.solid.app.ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.domain.Currency
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun CurrencyButton(
    modifier: Modifier = Modifier,
    currency: Currency?,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = themeColors().project.Weak,
        contentColor = themeColors().project.Normal,
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
            modifier = Modifier.weight(1f),
            text = currency?.name ?: stringResource(R.string.select_a_currency),
            style = UIKitTypography.Body1Medium16,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier,
            text = currency?.code.orEmpty(),
            style = UIKitTypography.Body1Medium16,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null
        )
    }
}