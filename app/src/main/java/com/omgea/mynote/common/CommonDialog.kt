package com.omgea.mynote.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen

enum class ButtonType {
    SOLID_BUTTON,
    TONAL_BUTTON,
    TEXT_BUTTON
}

@Composable
fun CommonDialog(
    modifier: Modifier,
    title : String = "",
    // password
    passwordValue: String,
    onPasswordValueChanged: (String) -> Unit,

    // dismiss Btn
    dismissButtonLabel: String = "",
    dismissAction: () -> Unit = {},

    // confirm Button
    confirmButtonLabel: String = "",
    confirmButtonType: ButtonType? = null,
    confirmButtonAction: () -> Unit = {},

    isErrorPassword: Boolean,
    keyboardAction: (KeyboardActionScope) -> Unit = {},

    ) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { dismissAction() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        text = {
            Column {
                if (isErrorPassword)
                    VisibilityAnimator(
                        isVisible = true,
                        errorMessage = stringResource(id = R.string.error)
                    )
                else
                    Text(text = "", Modifier.height(MaterialTheme.dimen.base_4x))
                PasswordTextField(
                    modifier = modifier
                        .fillMaxWidth(),
                    onValueChanged = onPasswordValueChanged,
                    placeholder = stringResource(id = R.string.password),
                    password = passwordValue,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword,
                    isError = isErrorPassword,
                    keyboardAction = keyboardAction,

                    )
            }
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

@Composable
@Preview(showBackground = true)
fun CommonDialogPreview() {
    CommonDialog(
        modifier = Modifier,
        dismissButtonLabel = "Cancel",
        dismissAction = {},
        confirmButtonAction = {},
        confirmButtonLabel = "Confirm",
        confirmButtonType = ButtonType.TONAL_BUTTON,
        onPasswordValueChanged = {},
        passwordValue = "",
        isErrorPassword = false,
        keyboardAction = {}

    )
}