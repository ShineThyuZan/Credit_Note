package com.omgea.mynote.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.omgea.mynote.R
import com.omgea.mynote.common.ButtonType

@Composable
fun LanguageDialog(
    modifier: Modifier,
    title: String = "",

    dismissButtonLabel: String = "",
    dismissAction: () -> Unit = {},

    confirmButtonLabel: String = "",
    confirmButtonType: ButtonType? = null,
    confirmButtonAction: () -> Unit = {},

    ) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { dismissAction() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        text = {
            Text(text = stringResource(id = R.string.lang_dialog_des))
        },
        dismissButton = {
            if (dismissButtonLabel.isNotEmpty()) {
                TextButton(onClick = { dismissAction() }) {
                    Text(
                        text = dismissButtonLabel,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        confirmButton = {
            confirmButtonType?.let {
                TextButton(onClick = confirmButtonAction) {
                    Text(
                        text = confirmButtonLabel,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        title = {
            val offset = Offset(5.0f, 10.0f)
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.width(300.dp),
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        offset = offset,
                        blurRadius = 3f
                    )
                )
            )
        }
    )
}