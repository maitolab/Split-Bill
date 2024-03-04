package com.solid.app.ui.base

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.solid.app.theme.themeColors
import io.chipmango.network.ResultWrapper
import io.chipmango.network.isError
import io.chipmango.network.isLoading
import io.chipmango.network.isSuccess
import io.chipmango.network.takeValue

@Composable
fun <T> StateLayout(
    modifier: Modifier,
    result: ResultWrapper<T>,
    contentPadding: PaddingValues,
    color: Color = themeColors().background.Normal,
    loadingContent: @Composable () -> Unit = { StateLoading() },
    errorContent: @Composable () -> Unit = { StateError() },
    successContent: @Composable (T) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = result,
        label = "state_layout",
        transitionSpec = {
            fadeIn(tween(500)) togetherWith fadeOut(tween(500))
        }
    ) { state ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = color
        ) {
            when {
                state.isLoading() -> loadingContent()
                state.isError() -> errorContent()
                state.isSuccess() -> {
                    val value = remember {
                        state.takeValue()
                    }
                    successContent(value)
                }
            }
        }
    }
}

@Composable
fun <T> StateLayout(
    modifier: Modifier,
    result: ResultWrapper<List<T>>,
    contentPadding: PaddingValues,
    containerColor: Color = themeColors().background.Normal,
    loadingContent: @Composable () -> Unit = { StateLoading() },
    errorContent: @Composable () -> Unit = { StateError() },
    emptyContent: @Composable () -> Unit,
    successContent: @Composable (List<T>) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = result,
        label = "state_layout",
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { state ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = containerColor
        ) {
            when {
                state.isLoading() -> loadingContent()
                state.isError() -> errorContent()
                state.isSuccess() -> {
                    val value = remember { state.takeValue() }
                    when {
                        value.isEmpty() -> emptyContent()
                        else -> successContent(value)
                    }
                }
            }
        }
    }
}