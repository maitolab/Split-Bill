package com.solid.app.ui.expense

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded._123
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.domain.Expense
import com.solid.app.domain.ExpenseUser
import com.solid.app.domain.Group
import com.solid.app.domain.SplitType
import com.solid.app.domain.User
import com.solid.app.extension.format
import com.solid.app.theme.themeColors
import com.solid.app.ui.activities.rememberPickMemberLauncher
import com.solid.app.ui.base.AmountInput
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.TextInput
import com.solid.app.ui.base.TopBarWithAction
import com.solid.app.ui.base.UserAvatar
import io.chipmango.theme.typography.UIKitTypography
import io.chipmango.uikit.datetime.rememberDatePicker
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentExpenseDetails(
    activity: AppCompatActivity = LocalContext.current as AppCompatActivity,
    title: String,
    group: Group,
    initialExpense: Expense? = null,
    initialExpenseUsers: List<ExpenseUser> = emptyList(),
    onBackClick: () -> Unit,
    onSaveClick: (expense: Expense, expenseUsers: List<ExpenseUser>) -> Unit
) {
    var date by remember {
        mutableStateOf(initialExpense?.createAt?.atOffset(ZoneOffset.UTC) ?: OffsetDateTime.now())
    }

    var description by remember {
        mutableStateOf(initialExpense?.description.orEmpty())
    }

    var cost by remember {
        mutableDoubleStateOf(initialExpense?.cost ?: 0.0)
    }

    var paidUser: User? by remember {
        mutableStateOf(initialExpenseUsers.find { it.paidShare == 1.0 }?.user)
    }

    val participants = remember {
        mutableStateMapOf<User, Double>().apply {
            initialExpenseUsers.forEach { this[it.user] = cost.times(it.ownedShare)}
        }
    }

    val pickMemberLauncher = rememberPickMemberLauncher {
        paidUser = it
    }

    val datePicker = rememberDatePicker(
        currentDateTime = date,
        positiveButtonText = stringResource(R.string.select),
        negativeButtonText = stringResource(R.string.cancel),
        onDateSelected = {
            date = it
        }
    )

    MyAppScaffold(
        topBar = {
            TopBarWithAction(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                onNavigationClicked = onBackClick,
                actionTitle = stringResource(R.string.save),
                actionEnabled = true,
                onActionClick = {
                    val expense =  Expense(
                        id = initialExpense?.id ?: 0L,
                        group = group,
                        description = description,
                        cost = cost,
                        createAt = date.toLocalDateTime(),
                        currency = group.currency,
                        splitType = SplitType.Equally,
                        modifiedAt = LocalDateTime.now(),
                        deletedAt = LocalDateTime.MAX
                    )
                    onSaveClick(
                        expense,
                        participants.map {
                            ExpenseUser(
                                expense = expense,
                                user = it.key,
                                paidShare = if (it.key == paidUser) 1.0 else 0.0,
                                ownedShare = it.value.div(cost)
                            )
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = {
                    description = it
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Notes, contentDescription = null)
                },
                label = stringResource(R.string.description)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AmountInput(
                modifier = Modifier.fillMaxWidth(),
                amount = cost,
                onAmountChanged = {
                    cost = it
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded._123, contentDescription = null)
                },
                label = stringResource(R.string.cost),
                suffix = {
                    Text(
                        modifier = Modifier,
                        text = group.currency.code,
                        style = UIKitTypography.Body1Regular16,
                        color = themeColors().text.Stronger
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                amountFormatter = { it.format() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClickableRow(
                title = stringResource(R.string.date),
                onClick = {
                    datePicker.show(activity.supportFragmentManager, "")
                }
            ) {
                Text(
                    modifier = Modifier,
                    text = date.format(DateTimeFormatter.ofPattern("dd.MMM yyyy")),
                    style = UIKitTypography.Body1Medium16,
                    color = themeColors().text.Stronger
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClickableRow(
                title = stringResource(R.string.paid_by),
                onClick = {
                    pickMemberLauncher.launch(group.id)
                }
            ) {
                if (paidUser != null) {
                    UserAvatar(
                        user = paidUser!!,
                        size = 24.dp
                    )
                }

                Text(
                    modifier = Modifier,
                    text = paidUser?.name ?: stringResource(R.string.tap_to_select),
                    style = UIKitTypography.Body1Medium16,
                    color = themeColors().text.Stronger
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpenseSplit(
                cost = cost,
                users = group.users,
                participants = participants,
                onAddParticipant = { user, amount ->
                    participants[user] = amount
                },
                onRemoveParticipant = { user, amount ->
                    participants.remove(user, amount)
                },
                onUserCostChanged = { user, amount ->
                    participants[user] = amount
                }
            )
        }
    }
}

@Composable
private fun ClickableRow(
    title: String,
    containerColor: Color = themeColors().background.Normal,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = containerColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = UIKitTypography.Body1Regular16,
            color = themeColors().text.Normal
        )

        content()

        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = themeColors().text.Normal
        )
    }
}