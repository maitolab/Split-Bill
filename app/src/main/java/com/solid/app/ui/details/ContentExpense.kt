package com.solid.app.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.ui.base.AppFloatingActionButton
import com.solid.app.ui.base.StateLayout
import com.solid.app.ui.base.list.ExpenseListLazyColumn
import com.solid.app.ui.expense.rememberCreateExpenseLauncher
import com.solid.app.ui.expense.rememberUpdateExpenseLauncher
import com.solid.app.viewmodel.ExpenseViewModel
import io.chipmango.network.ResultWrapper

@Composable
fun ContentExpense(groupId: Long, expenseViewModel: ExpenseViewModel = hiltViewModel()) {
    val expenses by remember {
        expenseViewModel.getExpenses(groupId)
    }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    val createExpenseLauncher = rememberCreateExpenseLauncher {

    }

    val updateExpenseLauncher = rememberUpdateExpenseLauncher {

    }

    StateLayout(
        modifier = Modifier.fillMaxSize(),
        result = expenses,
        contentPadding = PaddingValues(0.dp)
    ) { expenseList ->
        Box(modifier = Modifier.fillMaxSize()) {
            ExpenseListLazyColumn(
                modifier = Modifier.fillMaxSize(),
                expenseList = expenseList,
                contentPadding = PaddingValues(vertical = 16.dp),
                itemContentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                spacing = 0.dp,
                onExpenseClick = {
                    updateExpenseLauncher.launch(it.id)
                }
            )

            AppFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    createExpenseLauncher.launch(groupId)
                },
                shape = CircleShape,
                title = stringResource(id = R.string.new_expense),
                icon = Icons.Rounded.Add
            )
        }
    }
}