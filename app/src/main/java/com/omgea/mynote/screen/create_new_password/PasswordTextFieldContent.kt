package com.omgea.mynote.screen.create_new_password

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.omgea.mynote.R
import com.omgea.mynote.common.VerticalSpacerBase4x
import com.omgea.mynote.ui.theme.dimen

@Composable
fun PasswordTextFieldContent(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onPhoneValueChanged: (String) -> Unit,
    phoneImeAction: ImeAction,
    phonePlaceHolder: String,
    onPhoneValueCleared: () -> Unit,
    isErrorPhone: Boolean,
    errorMessagePhone: String,
    keyboardAction: (KeyboardActionScope) -> Unit = {}
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimen.base_2x),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                VerticalSpacerBase4x()
                PasswordTextFieldSection(
                    phoneNumber = phoneNumber,
                    phonePlaceHolder = phonePlaceHolder,
                    phoneImeAction = phoneImeAction,
                    onPhoneValueChanged = onPhoneValueChanged,
                    onPhoneValueCleared = onPhoneValueCleared,
                    isErrorPhone = isErrorPhone,
                    errorMessagePhone = errorMessagePhone,
                    keyboardAction = keyboardAction
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun VerifiedPhoneContentPreview() {
    Surface {
        PasswordTextFieldContent(
            phoneNumber = "",
            onPhoneValueChanged = {},
            phonePlaceHolder = stringResource(id = R.string.new_password),
            phoneImeAction = ImeAction.Done,
            onPhoneValueCleared = {},
            isErrorPhone = false,
            errorMessagePhone = ""
        )
    }
}
