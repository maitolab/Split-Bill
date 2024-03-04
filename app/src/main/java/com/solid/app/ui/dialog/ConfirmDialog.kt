package com.solid.app.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.solid.app.theme.themeColors
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun ConfirmDialog(
    dialogController: DialogController = rememberDialogController(),
    title: String,
    message: String,
    negativeText: String,
    positiveText: String,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    if (dialogController.isShowing()) {
        Dialog(
            onDismissRequest = {
                dialogController.hide()
            }
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = themeColors().background.Weaker
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = title,
                        color = themeColors().text.Stronger,
                        style = UIKitTypography.Title3SemiBold18
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = message,
                        color = themeColors().text.Normal,
                        style = UIKitTypography.Body1Regular16
                    )

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onNegativeClick() }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = negativeText,
                            style = UIKitTypography.Body1Regular16,
                            color = themeColors().text.Normal
                        )

                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onPositiveClick() }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = positiveText,
                            style = UIKitTypography.Body1Medium16,
                            color = themeColors().text.Stronger
                        )
                    }
                }
            }
        }
    }
}