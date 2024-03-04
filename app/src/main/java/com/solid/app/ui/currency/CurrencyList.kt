package com.solid.app.ui.currency

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.app.R
import com.solid.app.domain.Currency
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.Buttons
import com.solid.app.ui.base.TextInput
import com.solid.app.viewmodel.CurrencyViewModel
import io.chipmango.theme.typography.UIKitTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyList(
    modifier: Modifier,
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    currencies: List<Currency> = currencyViewModel.getSupportedCurrencies(),
    selectedCurrency: Currency? = null,
    onCurrencySelected: (Currency?) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    val currency by remember(selectedCurrency) {
        mutableStateOf(selectedCurrency)
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        stickyHeader {
            Surface(color = themeColors().background.Weaker) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.choose_currency),
                        style = UIKitTypography.Title3SemiBold18,
                        color = themeColors().text.Stronger,
                        textAlign = TextAlign.Center
                    )

                    TextInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                        },
                        label = stringResource(R.string.search_currency)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        items(currencies) {
            CurrencyItem(
                currency = it,
                isSelected = selectedCurrency == it,
                onCurrencySelected = onCurrencySelected
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        stickyHeader {
            Buttons(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.confirm).uppercase(),
                onClick = {
                    onCurrencySelected(currency)
                }
            )
        }
    }
}


@Composable
private fun CurrencyItem(
    currency: Currency,
    isSelected: Boolean,
    onCurrencySelected: (Currency) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) themeColors().project.Weak else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = {
                    onCurrencySelected(currency)
                }
            )
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(
            modifier = Modifier.weight(1f),
            text = currency.name,
            style = UIKitTypography.Body1Medium16,
            color = if (isSelected) themeColors().project.Normal else themeColors().text.Stronger
        )

        Text(
            modifier = Modifier,
            text = currency.code,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = UIKitTypography.Body1Medium16,
            color = if (isSelected) themeColors().project.Normal else themeColors().text.Stronger,
            textAlign = TextAlign.End
        )
    }
}