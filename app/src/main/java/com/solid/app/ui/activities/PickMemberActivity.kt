package com.solid.app.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.domain.User
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.DayNightTheme
import com.solid.app.ui.base.StateLayout
import com.solid.app.ui.base.TopBar
import com.solid.app.ui.user.UserListLazyColumn
import com.solid.app.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.network.ResultWrapper


@Composable
fun rememberPickMemberLauncher(onResult: (User?) -> Unit) = rememberLauncherForActivityResult(
    contract = PickGroupMemberContract(),
    onResult = {
        onResult(it)
    }
)

private class PickGroupMemberContract : ActivityResultContract<Long, User?>() {
    override fun createIntent(context: Context, input: Long): Intent {
        return PickMemberActivity.createIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        return PickMemberActivity.getResult(intent)
    }
}


@AndroidEntryPoint
class PickMemberActivity : AppCompatActivity() {

    companion object {
        private const val KEY_GROUP_ID = "key_group_id"
        private const val KEY_SELECTED_USER = "key_selected_user"

        fun createIntent(context: Context, groupId: Long): Intent {
            return Intent(context, PickMemberActivity::class.java).putExtra(KEY_GROUP_ID, groupId)
        }

        fun getResult(intent: Intent?): User? {
            return intent?.getParcelableExtra<User>(KEY_SELECTED_USER)
        }
    }

    private val groupId by lazy { intent.getLongExtra(KEY_GROUP_ID, 0L) }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val groupViewModel: GroupViewModel = hiltViewModel()
            val groupResult by groupViewModel.getGroup(groupId).collectAsStateWithLifecycle(
                initialValue = ResultWrapper.none()
            )
            DayNightTheme {
                StateLayout(
                    modifier = Modifier.fillMaxSize(),
                    result = groupResult,
                    contentPadding = PaddingValues(0.dp)
                ) { group ->
                    MyAppScaffold(
                        topBar = {
                            TopBar(
                                title = group.name,
                                onNavigationIconClicked = {
                                    setResult(Activity.RESULT_CANCELED)
                                    finish()
                                }
                            )
                        }
                    ) {
                        UserListLazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            spacing = 16.dp,
                            userList = group.users,
                            onUserClick = {
                                setResult(
                                    Activity.RESULT_OK,
                                    Intent().putExtra(KEY_SELECTED_USER, it)
                                )
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}