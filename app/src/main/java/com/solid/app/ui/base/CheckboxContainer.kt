package com.solid.app.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors

@Composable
fun CheckboxContainer(
    modifier: Modifier,
    isChecked: Boolean,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onToggleCheckedChange: () -> Unit,
    content: @Composable RowScope.(Boolean) -> Unit) {
    Row(
        modifier = modifier.clickable { onToggleCheckedChange() }.padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        content(isChecked)

        Spacer(modifier = Modifier.weight(1f))
        
        Icon(
            imageVector = if (isChecked) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = if (isChecked) themeColors().project.Normal else themeColors().text.Normal
        )
    }
}