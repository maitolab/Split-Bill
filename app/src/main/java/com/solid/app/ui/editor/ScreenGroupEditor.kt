package com.solid.app.ui.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.PersonAddAlt
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.composables.Separator
import com.solid.app.domain.Category
import com.solid.app.domain.Currency
import com.solid.app.domain.User
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.CurrencyButton
import com.solid.app.ui.base.IconTextButton
import com.solid.app.ui.base.Buttons
import com.solid.app.ui.base.StateLayout
import com.solid.app.ui.base.TextInput
import com.solid.app.ui.base.TopBar
import com.solid.app.ui.category.CategoryList
import com.solid.app.ui.dialog.ConfirmDialog
import com.solid.app.ui.dialog.rememberDialogController
import com.solid.app.ui.user.UserListColumn
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper
import io.chipmango.network.ResultWrapperCollector
import io.chipmango.network.rememberFlow
import io.chipmango.network.rememberFlowController
import io.chipmango.theme.typography.UIKitTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenGroupEditor(
    groupViewModel: GroupViewModel = hiltViewModel(),
    groupId: Long,
    onBackClick: () -> Unit,
    onAddMember: (Long) -> Unit,
    onGroupUpdated: () -> Unit,
    onGroupDeleted: () -> Unit
) {
    val groupResult by remember { groupViewModel.getGroup(groupId) }.collectAsStateWithLifecycle(
        initialValue = ResultWrapper.none()
    )

    val deleteDialogController = rememberDialogController()

    MyAppScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.info),
                onNavigationIconClicked = onBackClick,
                actions = {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null
                    )
                }
            )
        }
    ) {
        StateLayout(
            modifier = Modifier.fillMaxSize(),
            result = groupResult,
            contentPadding = PaddingValues(0.dp)
        ) { group ->
            var category: Category? by remember {
                mutableStateOf(group.category)
            }

            var currency: Currency? by remember {
                mutableStateOf(group.currency)
            }

            var groupName by remember {
                mutableStateOf(group.name)
            }

            val users = remember {
                mutableStateListOf<User>().apply { addAll(group.users) }
            }

            val updateGroupController = rememberFlowController()
            val updateGroupResult by rememberFlow(
                controller = updateGroupController,
                initialValue = ResultWrapper.none()
            ) {
                groupViewModel.updateGroup(
                    newGroup = group.copy(
                        name = groupName,
                        category = category ?: Category.default(),
                        currency = currency ?: Currency.default()
                    )
                )
            }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

            ResultWrapperCollector(
                resultWrapper = updateGroupResult,
                onSuccess = {
                    onGroupUpdated()
                }
            )

            val deleteGroupController = rememberFlowController()
            val deleteGroupResult by rememberFlow(
                controller = deleteGroupController,
                initialValue = ResultWrapper.none(),
                flowApi = {
                    groupViewModel.deleteGroup(group)
                }
            ).collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

            ResultWrapperCollector(
                resultWrapper = deleteGroupResult,
                onSuccess = {
                    onGroupDeleted()
                }
            )

            Column(
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState()
                    )
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = stringResource(R.string.group_name),
                    value = groupName,
                    onValueChange = {
                        groupName = it
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )

                CategoryList(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    selectedCategory = category,
                    onCategoryChanged = {
                        category = it
                    }
                )

                CurrencyButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    currency = currency,
                    onClick = {

                    }
                )

                Separator()

                Column {
                    IconTextButton(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        title = stringResource(id = R.string.add_members),
                        icon = Icons.Rounded.PersonAddAlt,
                        onClick = {
                            onAddMember(groupId)
                        }
                    )

                    IconTextButton(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        title = stringResource(R.string.delete_group),
                        icon = Icons.Rounded.Remove,
                        contentColor = themeColors().negative.Normal,
                        onClick = {
                            deleteDialogController.show()
                        }
                    )
                }

                Separator()

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "${users.size} members",
                    color = themeColors().text.Normal,
                    style = UIKitTypography.Body2Regular14
                )

                UserListColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    spacing = 8.dp,
                    userList = users
                )

                Separator()

                Buttons(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    title = stringResource(R.string.btn_update_group),
                    onClick = {
                        updateGroupController.retry()
                    },
                    enabled = true
                )
            }

            ConfirmDialog(
                dialogController = deleteDialogController,
                title = "Delete",
                message = "Are you sure",
                negativeText = "Cancel",
                positiveText = "Confirm",
                onNegativeClick = {
                    deleteDialogController.hide()
                },
                onPositiveClick = {
                    groupViewModel.deleteGroup(group)
                }
            )
        }


    }
}