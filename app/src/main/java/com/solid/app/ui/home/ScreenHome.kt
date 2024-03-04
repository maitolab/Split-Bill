package com.solid.app.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.domain.Group
import com.solid.app.ui.base.AppFloatingActionButton
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.StateLayout
import com.solid.app.ui.base.TopBar
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome(
    groupViewModel: GroupViewModel = hiltViewModel(),
    onNewGroup: () -> Unit,
    onGroupClick: (Group) -> Unit,
    onSettingClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val groups by remember { groupViewModel.getGroups() }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    MyAppScaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = onNewGroup,
                shape = CircleShape,
                title = "New Group",
                icon = Icons.Rounded.Add
            )
        },
        topBar = {
            TopBar(
                title = stringResource(R.string.groups),
                actions = {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onSearchClick() }
                            .padding(12.dp),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )

                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onSettingClick() }
                            .padding(12.dp),
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null
                    )
                }
            )
        }
    ) {
        StateLayout(
            modifier = Modifier.fillMaxSize(),
            result = groups,
            contentPadding = PaddingValues(0.dp),
            emptyContent = {
                ContentGroupEmptyList(
                    modifier = Modifier.fillMaxSize(),
                    onNewGroup = onNewGroup
                )
            },
            successContent = { groups ->
                ContentGroupList(
                    modifier = Modifier.fillMaxSize(),
                    groups = groups,
                    onGroupClick = onGroupClick
                )
            }
        )
    }
}