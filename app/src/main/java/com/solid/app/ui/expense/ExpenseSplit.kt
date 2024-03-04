package com.solid.app.ui.expense

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.domain.User
import com.solid.app.extension.format
import com.solid.app.extension.share
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.AmountInput
import com.solid.app.ui.base.TextInput
import com.solid.app.ui.base.UserAvatar
import io.chipmango.theme.typography.UIKitTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseSplit(
    cost: Double,
    users: List<User>,
    participants: Map<User, Double>,
    onAddParticipant: (User, Double) -> Unit,
    onRemoveParticipant: (User, Double) -> Unit,
    onUserCostChanged: (User, Double) -> Unit
) {
    val isAllChecked by remember {
        derivedStateOf { participants.size == users.size }
    }

    var checkError by remember {
        mutableLongStateOf(0L)
    }

    val isError by remember(checkError, cost) {
        derivedStateOf {
            participants.values.sum() != cost
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            SplitHeader(
                modifier = Modifier.fillMaxWidth(),
                isAllChecked = isAllChecked,
                users = users,
                participants = participants.keys.toList(),
                onToggleAllChecked = {
                    when {
                        isAllChecked -> {
                            participants.forEach { onRemoveParticipant(it.key, it.value) }
                            checkError = System.currentTimeMillis()
                        }

                        else -> {
                            val remainingUsers = users.filterNot { participants.containsKey(it) }
                            if (remainingUsers.isNotEmpty()) {
                                val costPerUser = (cost - participants.values.sum()).share(remainingUsers.size)
                                remainingUsers.forEach {
                                    onAddParticipant(it, costPerUser)
                                }
                            }
                            checkError = System.currentTimeMillis()
                        }
                    }
                },
                onEquallyClick = {
                    val costPerUser = cost.share(participants.size)
                    participants.forEach {
                        onUserCostChanged(it.key, costPerUser)
                    }
                    checkError = System.currentTimeMillis()
                }
            )
        }

        if (isError) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = themeColors().negative.Weaker,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    text = "Error",
                    color = themeColors().negative.Normal,
                    style = UIKitTypography.Body2Regular14
                )
            }
        }

        items(users) { user ->
            ExpenseUserItem(
                modifier = Modifier.fillMaxWidth(),
                user = user,
                cost = participants[user] ?: 0.0,
                isChecked = participants.contains(user),
                onToggleChecked = {
                    if (participants.contains(user)) {
                        onRemoveParticipant(user, participants[user] ?: 0.0)
                    } else {
                        onAddParticipant(user, cost - participants.values.sum())
                    }
                    checkError = System.currentTimeMillis()
                },
                onUserCostChanged = onUserCostChanged
            )
        }
    }
}

@Composable
private fun SplitHeader(
    modifier: Modifier,
    isAllChecked: Boolean,
    participants: List<User>,
    users: List<User>,
    onToggleAllChecked: () -> Unit,
    onEquallyClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.clickable { onToggleAllChecked() },
            imageVector = if (isAllChecked) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = if (isAllChecked) themeColors().project.Normal else themeColors().text.Normal
        )

        Text(
            text = buildString {
                append(participants.size)
                append("/")
                append(users.size)
                append(" participants")
            },
            style = UIKitTypography.Body2Regular14,
            color = themeColors().text.Stronger
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.clickable { onEquallyClick() },
            text = stringResource(id = R.string.split_equally),
            style = UIKitTypography.Body2Regular14,
            color = themeColors().project.Normal
        )
    }
}

@Composable
private fun ExpenseUserItem(
    modifier: Modifier,
    user: User,
    cost: Double,
    isChecked: Boolean,
    onToggleChecked: () -> Unit,
    onUserCostChanged: (User, Double) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.clickable { onToggleChecked() },
            imageVector = if (isChecked) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = if (isChecked) themeColors().project.Normal else themeColors().text.Normal
        )

        UserAvatar(
            user = user,
            size = 24.dp
        )

        Text(
            text = user.name,
            style = UIKitTypography.Body2Regular14,
            color = themeColors().text.Stronger
        )

        Spacer(modifier = Modifier.weight(1f))

        AmountInput(
            modifier = Modifier.width(150.dp),
            amount = cost,
            onAmountChanged = {
                onUserCostChanged(user, it)
            },
            textStyle = UIKitTypography.Body2Regular14.copy(textAlign = TextAlign.End),
            amountFormatter = { it.format() }
        )
    }
}