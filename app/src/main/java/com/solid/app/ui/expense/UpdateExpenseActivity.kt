package com.solid.app.ui.expense

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.solid.app.ui.base.DayNightTheme
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun rememberUpdateExpenseLauncher(onResult: () -> Unit) = rememberLauncherForActivityResult(
    contract = UpdateExpenseContract(),
    onResult = {
        onResult()
    }
)

private class UpdateExpenseContract : ActivityResultContract<Long, Unit>() {
    override fun createIntent(context: Context, input: Long): Intent {
        return UpdateExpenseActivity.createIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}

@AndroidEntryPoint
class UpdateExpenseActivity : AppCompatActivity() {

    companion object {
        private const val KEY_EXPENSE_ID = "key_expense_id"

        fun createIntent(context: Context, expenseId: Long): Intent {
            return Intent(context, UpdateExpenseActivity::class.java)
                .putExtra(KEY_EXPENSE_ID, expenseId)
        }
    }

    private val expenseId by lazy { intent.getLongExtra(KEY_EXPENSE_ID, 0L) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DayNightTheme {
                ScreenUpdateExpense(
                    expenseId = expenseId,
                    onBackClick = {
                        finish()
                    }
                )
            }
        }
    }
}