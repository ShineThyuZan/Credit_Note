package com.omgea.mynote.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen

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
                    vertical = MaterialTheme.dimen.small,
                    horizontal = MaterialTheme.dimen.base
                ),
                text = textFieldLabel,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimen.base_7x)
                .clip(RoundedCornerShape(MaterialTheme.dimen.base_2x))
                .border(
                    width = 0.5.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(MaterialTheme.dimen.base_2x)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimen.base)
                    .weight(1f, fill = false)
                ,
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
                            modifier = Modifier.padding(start = MaterialTheme.dimen.small)
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
                    onDone = keyboardAction,
                    onGo = keyboardAction,
                    onNext = keyboardAction

                ),
                cursorBrush = SolidColor(
                    value = MaterialTheme.colorScheme.primary
                )
            )
            if (value.isNotEmpty()) {
                IconButton(onClick = onValueCleared) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
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