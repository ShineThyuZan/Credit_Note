package com.omgea.mynote.screen.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.omgea.mynote.R
import com.omgea.mynote.common.CommonTextField
import com.omgea.mynote.screen.edit.date.DateSection
import com.omgea.mynote.ui.theme.dimen

@Composable
fun NoteEditContent(
    name: String,
    nameTextChange: (String) -> Unit,
    nameTextClear: () -> Unit,

    description: String,
    descriptionTextChange: (String) -> Unit,
    descriptionClear: () -> Unit,

    amount: String,
    amountTextChange: (String) -> Unit,
    amountTextClear: () -> Unit,
    keyboard: (KeyboardActionScope) -> Unit,

    dateValue: String,
    onDateChangeClicked: () -> Unit,
    errorDate: Boolean,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(84.dp))

        CommonTextField(
            placeholder = stringResource(id = R.string.name_place_holder),
            textFieldLabel = stringResource(id = R.string.name),
            /*   isError = isError,*/
            value = name,
            onValueChanged = nameTextChange,
            onValueCleared = nameTextClear,
            errorMessage = stringResource(id = R.string.error),
            keyboardAction = keyboard
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimen.base_2x))

        CommonTextField(
            placeholder = stringResource(id = R.string.description_place_holder),
            textFieldLabel = stringResource(id = R.string.description),
            /*   isError = isError,*/
            value = description,
            onValueChanged = descriptionTextChange,
            onValueCleared = descriptionClear,
            errorMessage = stringResource(id = R.string.error),
            keyboardAction = keyboard
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimen.base_2x))

        CommonTextField(
            placeholder = stringResource(id = R.string.amount_placeholder),
            textFieldLabel = stringResource(id = R.string.amount),
            /*   isError = isError,*/
            value = amount,
            onValueChanged = amountTextChange,
            onValueCleared = amountTextClear,
            errorMessage = stringResource(id = R.string.error),
            keyboardType = KeyboardType.Number,
            keyboardAction = keyboard
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.base_2x))

        DateSection(
            dobValue = dateValue,
            onDobChangeClicked = onDateChangeClicked,
            isError = errorDate,
        )
    }
}