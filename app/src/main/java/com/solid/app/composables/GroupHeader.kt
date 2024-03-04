package com.solid.app.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solid.app.domain.Group
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.RoundSpacer
import com.solid.app.ui.base.UserAvatarChain
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun GroupHeader(modifier: Modifier, contentPadding: PaddingValues, group: Group) {
    Row(
        modifier = modifier.padding(contentPadding),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundSpacer(modifier = Modifier.size(80.dp))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = group.name,
                style = UIKitTypography.Body1Medium16,
                color = themeColors().text.Stronger
            )

            UserAvatarChain(
                modifier = Modifier,
                users = group.users
            )
        }
    }
}