package com.omgea.mynote.screen.edit

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.R
import com.omgea.mynote.screen.edit.udf.EditAction
import com.omgea.mynote.screen.edit.udf.EditUIEvent
import com.omgea.mynote.ui.theme.MyNoteTheme
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NoteEditScreen(
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
                is EditUIEvent.SaveUser -> { navController.navigateUp() }
                EditUIEvent.HideDobPicker -> { datePickerDialog.hide() }
                EditUIEvent.ShowDobPicker -> { datePickerDialog.show() }
                is EditUIEvent.ShowSnackBar -> { scaffoldState.snackbarHostState.showSnackbar(message = event.message)
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
            NoteEditContent(
                /** Name */
                name = nameState!!,
                nameTextChange = { name ->
                    viewModel.onAction(EditAction.EnteredName(nameText = name))
                },
                nameTextClear = {
                    viewModel.onAction(EditAction.EnteredName(nameText = ""))
                },

                /** Description */
                description = lastNameState!!,
                descriptionTextChange = { description ->
                    viewModel.onAction(EditAction.EnteredDescription(descText = description))
                },
                descriptionClear = {
                    viewModel.onAction(EditAction.EnteredDescription(descText = ""))
                },

                /** Amount  */
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
        NoteEditContent(
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