package com.solid.app.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.solid.app.settings.nightMode
import com.solid.app.theme.themeColors
import io.chipmango.theme.theme.AppTheme

@Composable
fun DayNightTheme(content: @Composable () -> Unit) {
    val useDarkTheme by nightMode()
    AppTheme(useDarkTheme = useDarkTheme) {
        val systemUiController = rememberSystemUiController()
        val statusBarColor = themeColors().background.Weaker
        LaunchedEffect(useDarkTheme) {
            systemUiController.setStatusBarColor(
                color = statusBarColor
            )
        }
        content()
    }
}
