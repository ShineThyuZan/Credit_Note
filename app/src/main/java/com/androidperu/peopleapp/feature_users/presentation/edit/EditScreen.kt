package com.androidperu.peopleapp.feature_users.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androidperu.peopleapp.R
import com.androidperu.peopleapp.feature_users.presentation.common.CommonTextField
import com.androidperu.peopleapp.ui.theme.PeopleAppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val nameState = state.userName
    val lastNameState = state.lastName
    val ageState = state.age

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditViewModel.UiEvent.SaveUser -> {
                    navController.navigateUp()
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
            EditContent(
                name = nameState,
                description = lastNameState,
                amount = ageState,
                onEvent = { viewModel.onEvent(it) }
            )
        },
        bottomBar = {
            EditBottomBar(
                onInsertUser = { viewModel.onEvent(EditEvent.InsertUser) }
            )
        }
    )
}

@Composable
fun EditTopBar(topAppBarText: String) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
fun EditContent(
    name: String,
    description: String,
    amount: String,
    onEvent: (EditEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(44.dp))
        CommonTextField(
            placeholder = stringResource(id = R.string.name),
            /*   isError = isError,*/
            value = name,
            onValueChanged = { onEvent(EditEvent.EnteredName(it)) },
            /* onValueCleared = onGroupNameCleared,*/
            errorMessage = stringResource(id = R.string.error)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonTextField(
            placeholder = stringResource(id = R.string.description),
            /*   isError = isError,*/
            value = description,
            onValueChanged = { onEvent(EditEvent.EnteredDescription(it)) },
            /* onValueCleared = onGroupNameCleared,*/
            errorMessage = stringResource(id = R.string.error)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonTextField(
            placeholder = stringResource(id = R.string.amount),
            /*   isError = isError,*/
            value = amount,
            onValueChanged = { onEvent(EditEvent.EnterAmount(it)) },
            /* onValueCleared = onGroupNameCleared,*/
            errorMessage = stringResource(id = R.string.error)
        )
    }
}

@Composable
fun EditBottomBar(
    modifier: Modifier = Modifier,
    onInsertUser: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 14.dp),
        onClick = { onInsertUser() }
    ) {
        Text(text = stringResource(id = R.string.add_new_note))
    }
}

@Preview
@Composable
fun PreviewAddEditUserTopBar() {
    PeopleAppTheme {
        EditTopBar(
            topAppBarText = stringResource(id = R.string.add_new_note)
        )
    }
}

@Preview
@Composable
fun PreviewAddEditUserContent() {
    PeopleAppTheme {
        EditContent(
            name = "Ada",
            description = "Smith",
            amount = "20",
            onEvent = { }
        )
    }
}

@Preview
@Composable
fun PreviewAddEditBottomBar() {
    PeopleAppTheme {
        EditBottomBar {}
    }
}