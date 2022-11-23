package com.omgea.mynote.screen.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omgea.mynote.ui.theme.dimen
import com.omgea.mynote.R
import com.omgea.mynote.common.VisibilityAnimator


@Composable
fun DateText(
    modifier: Modifier = Modifier,
    text: String,
    onDateChangeClicked: () -> Unit,
    errorDob: Boolean,
    errorMessage: String,
    textFieldLabel: String = "",
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (textFieldLabel.isNotEmpty()) {
            Text(
                modifier = modifier.padding(
                    vertical = MaterialTheme.dimen.small,
                    horizontal = MaterialTheme.dimen.base
                ),
                text = textFieldLabel,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimen.base_7x)
                .clip(RoundedCornerShape(MaterialTheme.dimen.base_2x))
                .border(
                    width = 1.dp,
                    color = if (errorDob) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    },
                    shape = RoundedCornerShape(MaterialTheme.dimen.base_2x)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        start = MaterialTheme.dimen.base_2x
                    ),
                text = text.ifEmpty {
                    stringResource(id =  R.string.date)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (text.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onDateChangeClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24),
                    contentDescription = "Date pick",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        VisibilityAnimator(
            isVisible = errorDob,
            errorMessage = errorMessage
        )
    }
}