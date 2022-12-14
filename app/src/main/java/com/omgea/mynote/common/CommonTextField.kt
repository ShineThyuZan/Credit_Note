package com.omgea.mynote.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    textFieldLabel: String = "",
    placeholder: String,

    value: String = "",
    onValueChanged: (String) -> Unit = {},
    onValueCleared: () -> Unit = {},

    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Go,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardAction: (KeyboardActionScope) -> Unit = {},
) {

    Column(modifier = modifier.fillMaxWidth()) {
        if (textFieldLabel.isNotEmpty()) {
            Text(
                modifier = modifier.padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                ),
                text = textFieldLabel,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            BasicTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f, fill = false),
                value = value,
                onValueChange = {
                    if (it.length <= 30) {
                        onValueChanged(it)
                    }
                },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    innerTextField()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onDone = keyboardAction
                ),
                cursorBrush = SolidColor(
                    value = MaterialTheme.colorScheme.primary
                )
            )
            if (value.isNotEmpty()) {
                IconButton(onClick = onValueCleared) {
                    Icon(
                        painter = painterResource(id = com.omgea.mynote.R.drawable.ic_cross),
                        contentDescription = "Trailing Icon",
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

    }
}


@Composable
@Preview
private fun TextFieldPreview() {
    Surface {
        CommonTextField(
            textFieldLabel = "Name",
            placeholder = "Placeholder",
            value = "",
            onValueChanged = {},
            onValueCleared = {},
            isError = false,
            errorMessage = ""
        )
    }
}

@Composable
@Preview
private fun TextFieldErrorPreview() {
    Surface {
        CommonTextField(
            textFieldLabel = "Name",
            placeholder = "Placeholder",
            value = "Text Data",
            onValueChanged = {},
            onValueCleared = { },
            isError = true,
            errorMessage = "Error message"
        )
    }
}