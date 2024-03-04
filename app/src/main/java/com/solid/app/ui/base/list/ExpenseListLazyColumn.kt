package com.solid.app.ui.base.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solid.app.domain.Expense
import com.solid.app.domain.User
import com.solid.app.extension.format
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.TwoLineRow
import com.solid.app.ui.base.UserAvatar
import io.chipmango.theme.typography.UIKitTypography
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseListLazyColumn(
    modifier: Modifier,
    contentPadding: PaddingValues,
    itemContentPadding: PaddingValues,
    spacing: Dp,
    expenseList: List<Expense>,
    onExpenseClick: (Expense) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(expenseList) {
            ExpenseListItem(
                modifier = Modifier.fillMaxWidth(),
                itemContentPadding = itemContentPadding,
                expense = it,
                onExpenseClick = onExpenseClick
            )
        }
    }
}

@Composable
private fun ExpenseListItem(
    modifier: Modifier,
    expense: Expense,
    itemContentPadding: PaddingValues,
    onExpenseClick: (Expense) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onExpenseClick(expense) }
            .padding(itemContentPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = themeColors().project.Normal,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = expense.createAt.format(DateTimeFormatter.ofPattern("dd")),
                style = UIKitTypography.Body2Medium14,
                color = themeColors().negativeInvert.Stronger
            )

            Text(
                text = expense.createAt.format(DateTimeFormatter.ofPattern("MMM")).uppercase(),
                style = UIKitTypography.Body2Regular14,
                color = themeColors().negativeInvert.Stronger
            )
        }

        TwoLineRow(
            title = expense.description,
            description = expense.currency.format(expense.cost)
        )
    }
}