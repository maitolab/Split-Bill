package com.solid.app.ui.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solid.app.domain.User
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.UserAvatar
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun UserListLazyColumn(
    modifier: Modifier,
    contentPadding: PaddingValues,
    spacing: Dp,
    userList: List<User>,
    onUserClick: (User) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(userList) {
            UserListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUserClick(it) },
                user = it
            )
        }
    }
}

@Composable
fun UserListColumn(
    modifier: Modifier,
    contentPadding: PaddingValues,
    spacing: Dp,
    userList: List<User>,
    onUserClick: (User) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        userList.forEach {
            UserListItem(
                modifier = Modifier.fillMaxWidth().clickable { onUserClick(it) },
                user = it
            )
        }
    }
}

@Composable
private fun UserListItem(modifier: Modifier, user: User) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        UserAvatar(user = user)

        Text(
            text = user.name,
            color = themeColors().text.Stronger,
            style = UIKitTypography.Body2Regular14
        )
    }
}