package com.omgea.mynote.screen.edit

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.R
import com.omgea.mynote.common.CommonTextField
import com.omgea.mynote.screen.edit.udf.EditAction
import com.omgea.mynote.screen.edit.date.DateSection
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.ui.theme.dimen
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val nameState = state.userName
    val lastNameState = state.lastName
    val ageState = state.age
    val date = state.date
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            viewModel.onAction(
                EditAction.ChangeDob(
                    dob = "$year-${month + 1}-$dayOfMonth"
                )
            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditViewModel.EditUIEvent.SaveUser -> {
                    navController.navigateUp()
                }
                EditViewModel.EditUIEvent.HideDobPicker -> {
                    datePickerDialog.hide()
                }
                EditViewModel.EditUIEvent.ShowDobPicker -> {
                    datePickerDialog.show()
                }
                is EditViewModel.EditUIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            EditTopBar(
                topAppBarText = stringResource(id = R.string.add_new_note)
            )
        },
        content = {
            Modifier.padding(it)
            EditContent(
                /* name */
                name = nameState!!,
                nameTextChange = { name ->
                    viewModel.onAction(EditAction.EnteredName(nameText = name))
                },
                nameTextClear = {
                    viewModel.onAction(EditAction.EnteredName(nameText = ""))
                },

                /* description */
                description = lastNameState!!,
                descriptionTextChange = { description ->
                    viewModel.onAction(EditAction.EnteredDescription(descText = description))
                },
                descriptionClear = {
                    viewModel.onAction(EditAction.EnteredDescription(descText = ""))
                },

                /* amount  */
                amount = ageState!!,
                amountTextChange = { amount ->
                    viewModel.onAction(EditAction.EnterAmount(amountText = amount))
                },
                amountTextClear = {
                    viewModel.onAction(EditAction.EnterAmount(amountText = ""))
                },
                keyboard = { keyboardController?.hide() },
                dateValue = date!!,
                onDateChangeClicked = {
                    viewModel.onAction(
                        EditAction.ClickDobPicker
                    )
                },
                errorDate = state.error.isErrorDob
            )
        },
        bottomBar = {
            EditBottomBar(
                onInsertUser = {
                    viewModel.onAction(EditAction.InsertUser)
                },
                isEnableState = viewModel.state.value.isEnable!!
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewAddEditUserTopBar() {
    MyNoteTheme() {
        EditTopBar(
            topAppBarText = stringResource(id = R.string.add_new_note)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddEditUserContent() {
    MyNoteTheme {
        EditContent(
            name = "Ada",
            description = "Smith",
            amount = "20",
            nameTextChange = {},
            nameTextClear = {},
            descriptionClear = {},
            descriptionTextChange = {},
            amountTextClear = {},
            amountTextChange = {},
            keyboard = {},
            dateValue = "",
            onDateChangeClicked = {},
            errorDate = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddEditBottomBar() {
    MyNoteTheme {
        EditBottomBar(isEnableState = true, modifier = Modifier, onInsertUser = {})
    }
}