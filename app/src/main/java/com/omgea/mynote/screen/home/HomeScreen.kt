package com.omgea.mynote.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.R
import com.omgea.mynote.common.ButtonType
import com.omgea.mynote.common.CommonDialog
import com.omgea.mynote.graph.Destination
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.screen.home.components.CustomListItem
import com.omgea.mynote.screen.home.components.HomeAction
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.ui.theme.dimen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.homeEvent.collectLatest {
            when (it) {
                is HomeEvent.NavigateToEdit -> {
                    navController.navigate(
                        route = Destination.Edit.passId(it.userId)
                    )
                }
            }
        }
    }

    if (state.showDialog) {
        when (state.dialogType) {
            DialogType.NOTHING -> {}
            DialogType.EDIT -> {
                CommonDialog(
                    modifier = Modifier.fillMaxWidth(),
                    passwordValue = state.passwordForEdit,
                    dismissButtonLabel = stringResource(id = R.string.cancel),
                    dismissAction = {
                        viewModel.onAction(
                            HomeAction.ClickEditCancel
                        )
                    },
                    confirmButtonLabel = stringResource(id = R.string.OK),
                    confirmButtonType = ButtonType.SOLID_BUTTON,
                    confirmButtonAction = {
                        viewModel.onAction(HomeAction.ClickEditOk(user = state.editUser))
                    },

                    onPasswordValueChanged = {
                        viewModel.onAction(
                            HomeAction.PasswordEditValueChange(
                                passwordValueChange = it
                            )
                        )
                    },
                    isErrorPassword = state.isError,
                    keyboardAction = {}
                )
            }
            DialogType.DELETE -> {
                CommonDialog(
                    modifier = Modifier.fillMaxWidth(),
                    passwordValue = state.passwordForDelete,
                    dismissButtonLabel = stringResource(id = R.string.cancel),
                    dismissAction = {
                        viewModel.onAction(
                            HomeAction.ClickDeleteCancel
                        )
                    },
                    confirmButtonLabel = stringResource(id = R.string.OK),
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
                    keyboardAction = {}
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            HomeTopBar()
        },
        floatingActionButton = {
            HomeFab(
                onFabClicked = { navController.navigate(Destination.Edit.route) }
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
                    viewModel.onAction(
                        HomeAction.ClickEdit(user = it)
                    )
                },
                userVos = state.usersList
            )
        }
    )
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.note_list),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxSize()
                    .size(MaterialTheme.dimen.base)
                    .wrapContentSize(Alignment.Center)
            )
        },
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onDeleteUser: (userVo: UserVo) -> Unit,
    onEditUser: (userVo: UserVo) -> Unit,
    userVos: List<UserVo> = emptyList()
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
    ) {
        LazyColumn {
            items(userVos) { user ->
                CustomListItem(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.dimen.small,
                            end = MaterialTheme.dimen.small,
                        )
                        .clip(RectangleShape),
                    user = user,
                    onEditUser = { onEditUser(user) },
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
    MyNoteTheme(darkTheme = false) {
        HomeContent(onDeleteUser = {}, onEditUser = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserFab() {
    MyNoteTheme(darkTheme = false) {
        HomeFab()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserTopBar() {
    MyNoteTheme(darkTheme = false) {
        HomeTopBar()
    }
}

