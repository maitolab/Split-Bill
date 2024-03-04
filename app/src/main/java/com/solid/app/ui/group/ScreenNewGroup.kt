package com.solid.app.ui.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solid.app.R
import com.solid.app.domain.Category
import com.solid.app.domain.Currency
import com.solid.app.domain.Group
import com.solid.app.ui.base.AppModalBottomSheetLayout
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.CurrencyButton
import com.solid.app.ui.base.Buttons
import com.solid.app.ui.base.TextInput
import com.solid.app.ui.base.TopBar
import com.solid.app.ui.category.CategoryList
import com.solid.app.ui.currency.CurrencyList
import com.solid.app.viewmodel.GroupViewModel
import io.chipmango.network.ResultWrapper
import io.chipmango.network.ResultWrapperCollector
import io.chipmango.network.rememberFlow
import io.chipmango.network.rememberFlowController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenNewGroup(
    onBackClicked: () -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel(),
    onAddMemberToGroup: (groupId: Long) -> Unit
) {
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )
    )
    val coroutineScope = rememberCoroutineScope()

    var category: Category? by remember {
        mutableStateOf(null)
    }

    var currency: Currency? by remember {
        mutableStateOf(null)
    }

    var groupName by remember {
        mutableStateOf("")
    }

    val createGroupController = rememberFlowController()
    val createGroupFlow by rememberFlow(
        controller = createGroupController,
        initialValue = ResultWrapper.none()
    ) {
        groupViewModel.createGroup(
            group = Group(
                name = groupName,
                category = category ?: Category.default(),
                currency = currency ?: Currency.default()
            )
        )
    }.collectAsStateWithLifecycle(initialValue = ResultWrapper.none())

    ResultWrapperCollector(
        resultWrapper = createGroupFlow,
        onSuccess = {
            onAddMemberToGroup(it)
        }
    )

    AppModalBottomSheetLayout(
        scaffoldState = sheetState,
        sheetContent = {
            CurrencyList(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                selectedCurrency = currency,
                onCurrencySelected = { selectedCurrency ->
                    coroutineScope.launch {
                        currency = selectedCurrency
                        sheetState.bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        MyAppScaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.create_new_group),
                    onNavigationIconClicked = onBackClicked
                )
            }
        ) {
            Column(
                modifier = Modifier
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
                        coroutineScope.launch { sheetState.bottomSheetState.expand() }
                    }
                )

                Buttons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    title = stringResource(R.string.btn_create_group),
                    onClick = {
                        createGroupController.retry()
                    },
                    enabled = groupName.isNotEmpty() && currency != null && category != null
                )
            }
        }
    }
}