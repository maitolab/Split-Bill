package com.solid.app.ui.home

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solid.app.domain.Group
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.RoundSpacer
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun GroupListItem(
    modifier: Modifier,
    contentPadding: PaddingValues,
    title: String,
    summary: String,
    onGroupClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onGroupClick() }
            .padding(contentPadding),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundSpacer(modifier = Modifier.size(60.dp))

        Column {
            Text(
                text = title,
                style = UIKitTypography.Body1Medium16,
                color = themeColors().text.Stronger
            )

            Text(
                text = summary,
                style = UIKitTypography.Body1Regular16,
                color = themeColors().text.Normal
            )
        }
    }
}

@Preview(
    backgroundColor = Color.WHITE.toLong(),
    showBackground = true
)
@Composable
private fun Preview() {
    GroupListItem(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        title = "Group Name",
        summary = "Group description"
    ) {

    }
}