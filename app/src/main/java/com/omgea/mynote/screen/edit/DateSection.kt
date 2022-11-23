package com.omgea.mynote.screen.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.omgea.mynote.R

@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    dobValue: String,
    onDobChangeClicked: () -> Unit,
    isError: Boolean,
) {
    Column(modifier = modifier) {
        DateText(
            text = dobValue,
            onDateChangeClicked = onDobChangeClicked,
            errorDob = isError,
            errorMessage = stringResource(id = R.string.date_less_current),
            textFieldLabel = stringResource(id = R.string.date)
        )
    }
}