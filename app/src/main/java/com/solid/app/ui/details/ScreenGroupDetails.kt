package com.solid.app.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.composables.GroupHeader
import com.solid.app.composables.HorizontalViewPager
import com.solid.app.composables.Separator
import com.solid.app.composables.TabInfo
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.StateLayout
import com.solid.app.ui.base.TopBar
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenGroupDetails(
    groupId: Long,
    groupViewModel: GroupViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val group by remember { groupViewModel.getGroup(groupId) }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    MyAppScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.info),
                onNavigationIconClicked = onBackClick,
                actions = {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onEditClick(groupId) }
                            .padding(12.dp),
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null
                    )
                }
            )
        }
    ) {
        StateLayout(
            modifier = Modifier.fillMaxSize(),
            result = group,
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                GroupHeader(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    group = it
                )

                Separator()

                HorizontalViewPager(
                    modifier = Modifier.fillMaxSize(),
                    headerColor = Color.Transparent,
                    contentColor = themeColors().background.Normal,
                    tabs = listOf(TabInfo.Expense, TabInfo.Balance, TabInfo.Settlements),
                    content = { tabInfo ->
                        when (tabInfo) {
                            TabInfo.Expense -> ContentExpense(it.id)
                            TabInfo.Balance -> ContentBalance()
                            TabInfo.Settlements -> ContentSettlements()
                            else -> {}
                        }
                    },
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}