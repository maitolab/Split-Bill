package com.solid.app.ui.group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.domain.User
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.Buttons
import com.solid.app.ui.base.TextInput
import com.solid.app.ui.base.TopBar
import com.solid.app.ui.user.UserListLazyColumn
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper
import io.chipmango.network.ResultWrapperCollector
import io.chipmango.network.rememberFlow
import io.chipmango.network.rememberFlowController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddMember(
    groupId: Long,
    groupViewModel: GroupViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onCompleted: () -> Unit
) {
    val userList = remember {
        mutableStateListOf<User>()
    }

    val controller = rememberFlowController()
    val result by rememberFlow(
        controller = controller,
        initialValue = ResultWrapper.none(),
        flowApi = {
            groupViewModel.addUsersToGroup(groupId, userList)
        }
    ).collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    ResultWrapperCollector(
        resultWrapper = result,
        onSuccess = { onCompleted() }
    )

    MyAppScaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.add_members),
                onNavigationIconClicked = onBackClicked
            )
        },
        bottomBar = {
            Buttons(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .fillMaxWidth(),
                title = stringResource(R.string.btn_done),
                onClick = { controller.retry() }
            )
        }
    ) {
        var memberName by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier.imePadding().fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                value = memberName,
                onValueChange = { memberName = it },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            if (memberName.isNotEmpty()) {
                                userList.add(User(name = memberName))
                            }
                            memberName = ""
                        },
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                },
                label = stringResource(R.string.member_name),
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (memberName.isNotEmpty()) {
                            userList.add(User(name = memberName))
                        }
                        memberName = ""
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            UserListLazyColumn(
                modifier = Modifier.fillMaxWidth(),
                userList = userList,
                contentPadding = PaddingValues(16.dp),
                spacing = 16.dp
            )
        }
    }
}