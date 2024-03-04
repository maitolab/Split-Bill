package com.solid.app.ui.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.solid.app.domain.Currency
import com.solid.app.extension.format
import com.solid.app.extension.parseDouble
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun AmountInput(
    modifier: Modifier,
    amount: Double,
    amountFormatter: (Double) -> String = { cost -> cost.format() },
    amountParser: (String) -> Double = { cost -> cost.parseDouble() },
    onAmountChanged: (Double) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = UIKitTypography.Body2Medium14,
    label: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(8.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedLabelColor = themeColors().text.Normal,
        focusedLabelColor = themeColors().text.Stronger,
        focusedTextColor = themeColors().text.Stronger,
        cursorColor = themeColors().text.Stronger,
        unfocusedTextColor = themeColors().text.Stronger,
        unfocusedContainerColor = themeColors().background.Normal,
        focusedContainerColor = themeColors().background.Strong,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    )
) {
    var text by remember(amount) {
        mutableStateOf(amountFormatter(amount))
    }

    TextField(
        text,
        {
            text = it
            val newAmount = amountParser(text)
            if (newAmount != amount) {
                onAmountChanged(newAmount)
            }
        },
        modifier,
        enabled,
        readOnly,
        textStyle,
        label = label?.let {
            {
                Text(
                    text = label,
                    style = UIKitTypography.Body2Regular14
                )
            }
        },
        placeholder,
        leadingIcon,
        trailingIcon,
        prefix,
        suffix,
        supportingText,
        isError,
        visualTransformation,
        keyboardOptions,
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        interactionSource,
        shape,
        colors
    )
}