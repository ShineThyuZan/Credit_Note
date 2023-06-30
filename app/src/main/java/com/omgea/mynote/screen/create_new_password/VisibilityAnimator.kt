package com.omgea.mynote.screen.create_new_password

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.omgea.mynote.common.HorizontalSpacerBase
import com.omgea.mynote.ui.theme.dimen

@Composable
fun VisibilityAnimator(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    errorMessage: String,
    hasInfo: Boolean ?= false
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)) +
                expandVertically(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)) +
                shrinkVertically(animationSpec = tween(500))
    ) {
        val colorValue = if(hasInfo!!) {
            MaterialTheme.colorScheme.outline
        } else
        {
            MaterialTheme.colorScheme.error
        }
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimen.base_2x,
                    vertical = MaterialTheme.dimen.small
                ),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            color = colorValue

        )
    }
}

@Composable
fun VisibilityAnimatorOtp(
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
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier
                    .padding(
                        start = MaterialTheme.dimen.base_2x,
                        end = MaterialTheme.dimen.base_2x
                    ),
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun VisibilityAnimatorPhone(
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
                    top = MaterialTheme.dimen.small,
                    bottom = MaterialTheme.dimen.small,
                    end = MaterialTheme.dimen.base_2x
                ),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun VisibilityAnimatorRow(
    modifier: Modifier = Modifier,
    isErrorPhone: Boolean,
    errorMessagePhone: String,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        HorizontalSpacerBase()
        VisibilityAnimatorPhone(
            isVisible = isErrorPhone ,
            errorMessage = if (isErrorPhone) errorMessagePhone else ""
        )
    }
}
