package com.solid.app.ui.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardBackspace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.solid.app.theme.Colors
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: ImageVector? = Icons.Rounded.KeyboardBackspace,
    onNavigationIconClicked: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = UIKitTypography.Title3Medium18,
            )
        },
        navigationIcon = {
            if (navigationIcon != null) {
                Icon(
                    modifier = Modifier
                        .clickable { onNavigationIconClicked() }
                        .size(48.dp)
                        .padding(12.dp),
                    imageVector = navigationIcon,
                    contentDescription = null,
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = themeColors().background.Weaker,
            titleContentColor = themeColors().text.Stronger,
            actionIconContentColor = themeColors().text.Stronger,
            navigationIconContentColor = themeColors().text.Stronger
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithAction(
    modifier: Modifier,
    title: String,
    navigationIcon: ImageVector? = Icons.Rounded.KeyboardBackspace,
    onNavigationClicked: () -> Unit,
    actionTitle: String,
    onActionClick: () -> Unit,
    actionEnabled: Boolean
) {
    TopBar(
        modifier = modifier.fillMaxWidth(),
        title = title,
        navigationIcon = navigationIcon,
        onNavigationIconClicked = onNavigationClicked
    ) {
        AppTextButton(
            modifier = Modifier,
            text = actionTitle,
            enabled = actionEnabled,
            onClick = onActionClick
        )
    }
}