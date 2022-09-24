package com.androidperu.peopleapp.feature_users.presentation.common

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
import androidx.compose.material.*
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
import com.androidperu.peopleapp.R

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
    errorMessage: String,
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
                .height(56.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colors.error else
                        MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = MaterialTheme.colors.surface
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(start = 16.dp)
                    .weight(1f),
                value = password,
                onValueChange = {
                    if (it.length <= 24) {
                        onValueChanged(it)
                    }
                },
                textStyle = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface,
                ),
                decorationBox = { innerTextField ->
                    if (password.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.body1,
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
                    value = MaterialTheme.colors.primary
                )
            )

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Close Text",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }
        if (isError) {
            VisibilityAnimator(
                isVisible = isError,
                errorMessage = errorMessage
            )
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VisibilityAnimator(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    errorMessage: String,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1000)) +
                expandVertically(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(1000)) +
                shrinkVertically(animationSpec = tween(1000))
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                ),
            text = errorMessage,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.error
        )
    }
}

@Composable
@Preview
fun PasswordTextFieldPreview() {
    Surface {
        PasswordTextField(
            password = "",
            onValueChanged = {},
            isError = false,
            errorMessage = "",
            keyboardAction = {}
        )
    }
}

@Composable
@Preview
fun PasswordTextFieldErrorPreview() {
    Surface {
        PasswordTextField(
            password = "",
            onValueChanged = {},
            isError = true,
            errorMessage = "This is error",
            keyboardAction = {}
        )
    }
}