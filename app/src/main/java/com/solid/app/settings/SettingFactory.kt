package com.solid.app.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import io.chipmango.persistence.Setting
import io.chipmango.persistence.booleanState

object SettingNightMode : Setting<Boolean>("key_night_mode", false)

@Composable
fun nightMode(): State<Boolean> = SettingNightMode.booleanState(true)