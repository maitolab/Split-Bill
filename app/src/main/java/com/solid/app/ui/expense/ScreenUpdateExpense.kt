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
import io.chipmango.network.ResultWrapper
import io.chipmango.network.ResultWrapperCollector
import io.chipmango.network.isSuccess
import io.chipmango.network.rememberFlow
import io.chipmango.network.rememberFlowController
import io.chipmango.network.takeValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUpdateExpense(
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    expenseId: Long,
    onBackClick: () -> Unit
) {
    val expenseResult by remember { expenseViewModel.getExpendDetails(expenseId) }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())
    StateLayout(
        modifier = Modifier.fillMaxSize(),
        result = expenseResult,
        contentPadding = PaddingValues(0.dp)
    ) { expense ->
        val expenseUsersResult by remember { expenseViewModel.getExpenseUsers(expenseId) }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

        StateLayout(
            modifier = Modifier.fillMaxSize(),
            result = expenseUsersResult,
            contentPadding = PaddingValues(0.dp)
        ) { expenseUsers ->
            var updatedExpense by remember {
                mutableStateOf<Expense?>(null)
            }

            val updatedExpenseUsers = remember {
                mutableStateListOf<ExpenseUser>()
            }

            val updateExpenseController = rememberFlowController()
            val createExpenseResult by rememberFlow(
                controller = updateExpenseController,
                initialValue = ResultWrapper.none()
            ) {
                expenseViewModel.updateExpense(
                    expense = updatedExpense,
                    expenseUsers = updatedExpenseUsers
                )
            }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

            ResultWrapperCollector(
                resultWrapper = createExpenseResult,
                onSuccess = {
                    onBackClick()
                }
            )

            ContentExpenseDetails(
                title = stringResource(id = R.string.update_expense),
                group = expense.group!!,
                initialExpense = expense,
                initialExpenseUsers = expenseUsers,
                onBackClick = onBackClick
            ) { _expense, _expenseUsers ->
                updatedExpense = _expense
                updatedExpenseUsers.clear()
                updatedExpenseUsers.addAll(_expenseUsers)

                updateExpenseController.retry()
            }
        }
    }
}