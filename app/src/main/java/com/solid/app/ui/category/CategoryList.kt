package com.solid.app.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.rounded.BabyChangingStation
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.Games
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.solid.app.R
import com.solid.app.domain.Category
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.ChipItem
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun CategoryList(
    modifier: Modifier,
    contentPadding: PaddingValues,
    selectedCategory: Category? = null,
    categories: List<Category> = Category.supportedCategories(),
    onCategoryChanged: (Category) -> Unit
) {
    
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = contentPadding
    ) {
        items(categories) {
            CategoryItem(
                category  = it,
                isSelect = selectedCategory == it,
                onClicked = {
                    onCategoryChanged(it)
                }
            )
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    isSelect: Boolean,
    onClicked: () -> Unit
) {
    ChipItem(
        modifier = modifier,
        onClick = onClicked,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = categoryIcon(category = category),
                contentDescription = null,
                tint = if (isSelect) themeColors().project.Normal else themeColors().text.Normal
            )
        },
        label = {
            Text(
                modifier = Modifier,
                text = stringCategoryName(category = category),
                style = UIKitTypography.Caption1Medium12,
                color = if (isSelect) themeColors().project.Normal else themeColors().text.Normal
            )
        },
        border = AssistChipDefaults.assistChipBorder(
            borderColor = if (isSelect) themeColors().project.Normal else themeColors().divider.Stronger
        )
    )
}

@Composable
@ReadOnlyComposable
private fun stringCategoryName(category: Category) : String {
    val id = when (category) {
        Category.Trip -> R.string.category_trip
        Category.Couple -> R.string.category_couple
        Category.Shopping -> R.string.category_shopping
        Category.Food -> R.string.category_food
        Category.Sports -> R.string.category_sports
        Category.Games -> R.string.category_games
        Category.Kids -> R.string.category_kids
        else -> R.string.category_others
    }
    return stringResource(id = id)
}

@Composable
@ReadOnlyComposable
private fun categoryIcon(category: Category) : ImageVector {
    return  when (category) {
        Category.Trip -> Icons.Rounded.FlightTakeoff
        Category.Couple -> Icons.Rounded.FavoriteBorder
        Category.Shopping -> Icons.Rounded.ShoppingCart
        Category.Food -> Icons.Rounded.Fastfood
        Category.Sports -> Icons.Rounded.SportsSoccer
        Category.Games -> Icons.Rounded.Games
        Category.Kids -> Icons.Rounded.BabyChangingStation
        else -> Icons.Rounded.ListAlt
    }
}

