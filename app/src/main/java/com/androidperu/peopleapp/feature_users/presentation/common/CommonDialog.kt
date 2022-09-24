package com.androidperu.peopleapp.feature_users.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

enum class ButtonType {
    SOLID_BUTTON,
    TONAL_BUTTON,
    TEXT_BUTTON
}

@Composable
fun CommonDialog(
    modifier: Modifier,
    passwordValue: String,
    onPasswordValueChanged: (String) -> Unit,

    dismissButtonLabel: String = "",
    dismissAction: () -> Unit = {},

    confirmButtonLabel: String = "",
    confirmButtonType: ButtonType? = null,
    confirmButtonAction: () -> Unit = {},

    isErrorPassword: Boolean = false,
    errorMessagePassword: String = "Type mismatch",
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
                Text(text = "", Modifier.height(36.dp))

                PasswordTextField(
                    modifier = modifier
                        .fillMaxWidth(),
                    onValueChanged = onPasswordValueChanged,
                    placeholder = "Password",
                    password = passwordValue,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    isError = isErrorPassword,
                    errorMessage = errorMessagePassword,
                    keyboardAction = keyboardAction
                )
            }

        },
        dismissButton = {
            if (dismissButtonLabel.isNotEmpty()) {
                TextButton(onClick = { dismissAction() }) {
                    Text(
                        text = dismissButtonLabel,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        },
        confirmButton = {
            confirmButtonType?.let {
                TextButton(onClick = confirmButtonAction) {
                    Text(
                        text = confirmButtonLabel,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        },
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
        errorMessagePassword = "",
        keyboardAction = {}

    )
}