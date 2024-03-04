package com.solid.app.ui.expense

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.ui.base.StateLayout
import com.solid.app.viewmodel.ExpenseViewModel
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper
import io.chipmango.network.ResultWrapperCollector
import io.chipmango.network.rememberFlow
import io.chipmango.network.rememberFlowController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCreateExpense(
    groupViewModel: GroupViewModel = hiltViewModel(),
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    groupId: Long,
    onBackClick: () -> Unit
) {
    val groupResult by remember { groupViewModel.getGroup(groupId) }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    StateLayout(
        modifier = Modifier.fillMaxSize(),
        result = groupResult,
        contentPadding = PaddingValues(0.dp)
    ) { group ->

        var newExpense by remember {
            mutableStateOf<Expense?>(null)
        }

        val newExpenseUsers = remember {
            mutableStateListOf<ExpenseUser>()
        }

        val createExpenseController = rememberFlowController()
        val createExpenseResult by rememberFlow(
            controller = createExpenseController,
            initialValue = ResultWrapper.none()
        ) {
            expenseViewModel.addExpense(
                expense = newExpense,
                expenseUsers = newExpenseUsers
            )
        }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

        ResultWrapperCollector(
            resultWrapper = createExpenseResult,
            onSuccess = {
                onBackClick()
            }
        )

        ContentExpenseDetails(
            title = stringResource(id = R.string.new_expense),
            group = group,
            onBackClick = onBackClick
        ) { expense, expenseUsers ->
            newExpense = expense
            newExpenseUsers.clear()
            newExpenseUsers.addAll(expenseUsers)
            createExpenseController.retry()
        }
    }
}