package com.solid.app.ui.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.domain.User
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun UserAvatar(
    user: User,
    size: Dp = 28.dp
) {
    Icon(
        modifier = Modifier.size(size),
        painter = painterResource(id = R.drawable.ic_avatar),
        contentDescription = null,
        tint = Color.Unspecified
    )
}

@Composable
fun UserAvatarChain(modifier: Modifier, users: List<User>) {
    val moreCounting by remember(users) {
        derivedStateOf { users.size - 4 }
    }


    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-6).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(users.take(4)) {
            UserAvatar(
                user = it,
                size = 28.dp
            )
        }

        if (moreCounting > 0) {
            item {
                Text(
                    modifier = Modifier.padding(start = 14.dp),
                    text = "+${moreCounting}",
                    style = UIKitTypography.Body2SemiBold14,
                    color = themeColors().text.Stronger
                )
            }
        }
    }
}