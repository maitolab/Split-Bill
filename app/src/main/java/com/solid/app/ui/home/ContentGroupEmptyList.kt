package com.solid.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.Buttons
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun ContentGroupEmptyList(modifier: Modifier, onNewGroup: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You are all set to go",
            style = UIKitTypography.Title1Medium24,
            color = themeColors().text.Stronger
        )

        Text(
            text = "Start by creating your first list. Or follow the link in your received invitation to join an existing list",
            style = UIKitTypography.Body1Regular16,
            color = themeColors().text.Normal,
            textAlign = TextAlign.Center
        )

        Buttons(
            title = "Add your first list",
            onClick = onNewGroup
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ContentGroupEmptyList(modifier = Modifier.fillMaxSize()) {

    }
}