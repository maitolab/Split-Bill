package com.solid.app.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class DialogController {
    private var showDialog by mutableStateOf(false)

    fun show() {
        showDialog = true
    }

    fun hide() {
        showDialog = false
    }

    fun isShowing(): Boolean {
        return showDialog
    }
}

@Composable
fun rememberDialogController() = remember {
    DialogController()
}