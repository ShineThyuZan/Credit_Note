package com.androidperu.peopleapp.feature_users.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androidperu.peopleapp.R
import com.androidperu.peopleapp.feature_users.domain.model.User
import com.androidperu.peopleapp.feature_users.presentation.Screen
import com.androidperu.peopleapp.feature_users.presentation.common.ButtonType
import com.androidperu.peopleapp.feature_users.presentation.common.CommonDialog
import com.androidperu.peopleapp.feature_users.presentation.home.components.HomeAction
import com.androidperu.peopleapp.feature_users.presentation.home.components.UserItem
import com.androidperu.peopleapp.ui.theme.PeopleAppTheme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    if (state.shouldShowRemoveDialog!!) {
        CommonDialog(
            modifier = Modifier.fillMaxWidth(),
            passwordValue = state.password,
            dismissButtonLabel = "Cancel",
            dismissAction = {
                viewModel.onAction(
                    HomeAction.ClickDeleteCancel
                )
            },
            confirmButtonLabel = "OK",
            confirmButtonType = ButtonType.SOLID_BUTTON,
            confirmButtonAction = {
                viewModel.onAction(HomeAction.ClickDeleteOk)
            },

            onPasswordValueChanged = {
                viewModel.onAction(
                    HomeAction.PasswordValueChange(
                        passwordValueChange = it
                    )
                )
            },
            isErrorPassword = state.isError,
            errorMessagePassword = state.errorText,
            keyboardAction = {}
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        floatingActionButton = {
            HomeFab(
                onFabClicked = { navController.navigate(Screen.Edit.route) }
            )
        },
        content = { innerPadding ->
            HomeContent(
                modifier = Modifier.padding(innerPadding),
                onDeleteUser = {
                    viewModel.onAction(
                        HomeAction.ClickDelete(user = it)
                    )
                    /*  viewModel.onEvent(HomeEvent.DeleteUser(it))*/

                },
                onEditUser = {
                    navController.navigate(
                        route = Screen.Edit.passId(it)
                    )
                },
                users = state.usersList
            )
        }
    )
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.note_list),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onDeleteUser: (user: User) -> Unit,
    onEditUser: (id: Int?) -> Unit,
    users: List<User> = emptyList()
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = modifier
    ) {
        LazyColumn {
            items(users) { user ->
                UserItem(
                    user = user,
                    onEditUser = { onEditUser(user.id) },
                    onDeleteUser = { onDeleteUser(user) }
                )
            }
        }
    }
}

@Composable
fun HomeFab(
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onFabClicked,
        modifier = modifier
            .height(52.dp)
            .widthIn(min = 52.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(id = R.string.add_new_note)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserContent() {
    PeopleAppTheme(darkTheme = false) {
        HomeContent(onDeleteUser = {}, onEditUser = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserFab() {
    PeopleAppTheme(darkTheme = false) {
        HomeFab()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserTopBar() {
    PeopleAppTheme(darkTheme = false) {
        HomeTopBar()
    }
}

