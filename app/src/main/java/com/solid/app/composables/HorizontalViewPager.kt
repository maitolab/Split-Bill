package com.solid.app.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography
import kotlinx.coroutines.launch

sealed class TabInfo(@StringRes val titleStringId: Int) {
    data object Expense : TabInfo(R.string.expense)
    data object Balance : TabInfo(R.string.balance)
    data object Settlements : TabInfo(R.string.settlements)

    data object SplitEqually: TabInfo(R.string.split_equally)
    data object SplitUnequally: TabInfo(R.string.split_unequally)
    data object SplitPercentage: TabInfo(R.string.split_percentage)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalViewPager(
    modifier: Modifier,
    headerColor: Color,
    contentColor: Color,
    tabs: List<TabInfo>,
    contentPadding: PaddingValues,
    content: @Composable (tabInfo: TabInfo) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        tabs.size
    }

    Column(modifier = modifier) {
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor),
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = themeColors().project.Normal,
                    height = 2.dp
                )
            }
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                Tab(
                    selected = pagerState.currentPage == tabIndex,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(tabIndex)
                        }
                    },
                    text = {
                        Text(
                            text = stringResource(id = tab.titleStringId),
                            style = UIKitTypography.Body2Medium14
                        )
                    },
                    selectedContentColor = themeColors().text.Stronger,
                    unselectedContentColor = themeColors().text.Normal
                )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .background(contentColor),
            state = pagerState,
            contentPadding = contentPadding
        ) {
            content(tabs[it])
        }
    }
}