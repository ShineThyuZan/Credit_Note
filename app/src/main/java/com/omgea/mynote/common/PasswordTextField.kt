package com.omgea.mynote.common

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R
import com.omgea.mynote.ui.theme.dimen

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "Placeholder",
    password: String = "Value",
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    keyboardAction: (KeyboardActionScope) -> Unit,
    isError: Boolean,
) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val icon =
        if (passwordVisibility) painterResource(id = R.drawable.ic_small_eye_off)
        else painterResource(id = R.drawable.ic_small_eye)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimen.base_7x)
                .clip(
                    RoundedCornerShape(MaterialTheme.dimen.base_2x)
                )
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else
                        MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(MaterialTheme.dimen.base_2x)
                )
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(start = MaterialTheme.dimen.base_2x)
                    .weight(1f),
                value = password,
                onValueChange = {
                    if (it.length <= 24) {
                        onValueChanged(it)
                    }
                },
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                decorationBox = { innerTextField ->
                    if (password.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelSmall,
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
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                cursorBrush = SolidColor(
                    value = MaterialTheme.colorScheme.primary
                )
            )

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Close Text",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
      /*  if (isError) {
            VisibilityAnimator(
                isVisible = isError,
            )
        }*/
    }
}


@Composable
fun VisibilityAnimator(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    errorMessage: String,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)) +
                expandVertically(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)) +
                shrinkVertically(animationSpec = tween(500))
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimen.base_2x,
                    vertical = MaterialTheme.dimen.small
                ),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}
@Composable
@Preview(showBackground = true)
fun PasswordTextFieldPreview() {
    Surface {
        PasswordTextField(
            password = "",
            onValueChanged = {},
            isError = false,
            keyboardAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PasswordTextFieldErrorPreview() {
    Surface {
        PasswordTextField(
            password = "",
            onValueChanged = {},
            isError = true,
            keyboardAction = {}
        )
    }
}