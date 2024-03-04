package com.solid.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solid.app.domain.Group

@Composable
fun ContentGroupList(modifier: Modifier, groups: List<Group>, onGroupClick: (Group) -> Unit) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(groups) {
            GroupListItem(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                title = it.name,
                summary = it.category.id,
                onGroupClick = {
                    onGroupClick(it)
                }
            )
        }
    }
}