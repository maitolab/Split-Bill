package com.solid.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.solid.app.navigation.AppNavigator
import com.solid.app.settings.nightMode
import com.solid.app.theme.themeColors
import com.solid.app.ui.base.DayNightTheme
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.theme.theme.AppTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DayNightTheme {
                AppNavigator()
            }
        }
    }
}