package com.solid.app.ui.setting

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.solid.app.settings.SettingNightMode
import com.solid.app.settings.nightMode
import com.solid.app.ui.base.MyAppScaffold
import com.solid.app.ui.base.TopBar
import com.solid.app.ui.base.settings.SettingSwitch
import io.chipmango.persistence.saveBoolean

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSettings(
    context: Context = LocalContext.current,
    onBackClick: () -> Unit
) {
    val nightMode by nightMode()

    MyAppScaffold(
        topBar = {
            TopBar(
                title = "Settings",
                onNavigationIconClicked = onBackClick
            )
        }
    ) {
        Column {
            SettingSwitch(
                modifier = Modifier.fillMaxWidth(),
                title = "Night Mode",
                checked = nightMode,
                onCheckedChange = {
                    SettingNightMode.saveBoolean(context, it)
                }
            )
        }
    }
}