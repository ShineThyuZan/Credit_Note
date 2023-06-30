package com.omgea.mynote.screen.create_new_password

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R
import com.omgea.mynote.common.HorizontalSpacerBase
import com.omgea.mynote.ui.theme.dimen

@Composable
fun PasswordTextFieldSection(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    phonePlaceHolder: String,
    phoneImeAction: ImeAction,
    onPhoneValueChanged: (String) -> Unit,
    onPhoneValueCleared: () -> Unit,
    isErrorPhone: Boolean,
    errorMessagePhone: String,
    keyboardAction: (KeyboardActionScope) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimen.base_7x)
        ) {
            HorizontalSpacerBase()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimen.base_7x)
                    .clip(
                        shape = RoundedCornerShape(
                            corner = CornerSize(MaterialTheme.dimen.base)
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = if (isErrorPhone) MaterialTheme.colorScheme.error else
                            MaterialTheme.colorScheme.onSurface.copy(0.5f),
                        shape = RoundedCornerShape(
                          corner = CornerSize(MaterialTheme.dimen.base)
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .padding(start = MaterialTheme.dimen.base_2x)
                        .weight(1f),
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 15) {
                            onPhoneValueChanged(it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    decorationBox = { innerTextField ->
                        if (phoneNumber.isEmpty()) {
                            Text(
                                text = phonePlaceHolder,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        innerTextField()
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = phoneImeAction,

                        ),
                    keyboardActions = KeyboardActions(
                        onDone = keyboardAction,
                        onNext = keyboardAction,
                        onGo = keyboardAction,
                        onPrevious = keyboardAction,
                        onSearch = keyboardAction,
                        onSend = keyboardAction,
                    ),
                    cursorBrush = SolidColor(
                        value = MaterialTheme.colorScheme.primary
                    )
                )

                if (phoneNumber.isNotEmpty()) {
                    IconButton(onClick = onPhoneValueCleared) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cross),
                            contentDescription = "Close Text",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant

                        )
                    }
                }
            }
        }
        VisibilityAnimatorRow(
            isErrorPhone = isErrorPhone,
            errorMessagePhone = errorMessagePhone
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun RegionSectionTextPreview() {
    Surface {
        PasswordTextFieldSection(
            phonePlaceHolder = "phone place holder",
            phoneNumber = "2057419",
            phoneImeAction = ImeAction.Next,
            onPhoneValueChanged = {},
            onPhoneValueCleared = {},
            isErrorPhone = false,
            errorMessagePhone = ""
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegionSectionPreview() {
    Surface {
        PasswordTextFieldSection(
            phonePlaceHolder = "phone place holder",
            phoneNumber = "",
            phoneImeAction = ImeAction.Next,
            onPhoneValueChanged = {},
            onPhoneValueCleared = {},
            isErrorPhone = false,
            errorMessagePhone = ""
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegionSectionErrorPreview() {
    Surface {
        PasswordTextFieldSection(
            phonePlaceHolder = "phone place holder",
            errorMessagePhone = "error phone",
            phoneNumber = "",
            phoneImeAction = ImeAction.Next,
            onPhoneValueChanged = {},
            onPhoneValueCleared = {},
            isErrorPhone = true,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegionSectionRegionErrorPreview() {
    Surface {
        PasswordTextFieldSection(
            phonePlaceHolder = "phone place holder",
            errorMessagePhone = "error phone",
            phoneNumber = "",
            phoneImeAction = ImeAction.Next,
            onPhoneValueChanged = {},
            onPhoneValueCleared = {},
            isErrorPhone = false,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegionSectionPhoneErrorPreview() {
    Surface {
        PasswordTextFieldSection(
            phonePlaceHolder = "phone place holder",
            errorMessagePhone = "error phone",
            phoneNumber = "",
            phoneImeAction = ImeAction.Next,
            onPhoneValueChanged = {},
            onPhoneValueCleared = {},
            isErrorPhone = true,
        )
    }
}