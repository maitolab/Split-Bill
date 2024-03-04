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
fun rememberCreateExpenseLauncher(onResult: () -> Unit) = rememberLauncherForActivityResult(
    contract = CreateExpenseContract(),
    onResult = {
        onResult()
    }
)

private class CreateExpenseContract : ActivityResultContract<Long, Unit>() {
    override fun createIntent(context: Context, input: Long): Intent {
        return CreateExpenseActivity.createIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}

@AndroidEntryPoint
class CreateExpenseActivity : AppCompatActivity() {

    companion object {
        private const val KEY_GROUP_ID = "key_group_id"

        fun createIntent(context: Context, groupId: Long): Intent {
            return Intent(context, CreateExpenseActivity::class.java).putExtra(KEY_GROUP_ID, groupId)
        }
    }

    private val groupId by lazy { intent.getLongExtra(KEY_GROUP_ID, 0L) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DayNightTheme {
                ScreenCreateExpense(
                    groupId = groupId,
                    onBackClick = {
                        finish()
                    }
                )
            }
        }
    }
}