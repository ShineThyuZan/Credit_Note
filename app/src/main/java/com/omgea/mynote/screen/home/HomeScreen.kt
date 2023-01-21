package com.omgea.mynote.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.R
import com.omgea.mynote.common.*
import com.omgea.mynote.graph.Destination
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.screen.home.components.DialogType
import com.omgea.mynote.screen.home.components.HomeAction
import com.omgea.mynote.screen.home.components.HomeEvent
import com.omgea.mynote.screen.home.components.HomeViewModel
import com.omgea.mynote.screen.home.sheet_view.MoreActionSheetView
import com.omgea.mynote.screen.home.sheet_view.MoreActionStatus
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.ui.theme.dimen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val DEFAULT_PASSWORD = "0000"

    BackHandler(enabled = modalBottomSheetState.isVisible) {
        scope.launch {
            modalBottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.homeEvent.collectLatest {
            when (it) {
                is HomeEvent.NavigateToEdit -> {
                    navController.navigate(
                        route = Destination.Edit.passId(it.userId)
                    )
                }
                HomeEvent.ShowMenu -> {
                    modalBottomSheetState.show()
                }
                HomeEvent.NavigateToCreatePassword -> {
                    navController.navigate(
                        route = Destination.CreateNewPassword.route
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
                    passwordValue = state.password,
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
                    keyboardAction = {},
                    title = stringResource(id = R.string.edit)
                )
            }
            DialogType.DELETE -> {
                CommonDialog(
                    modifier = Modifier.fillMaxWidth(),
                    passwordValue = state.password,
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
                    keyboardAction = {},
                    title = stringResource(id = R.string.delete)
                )
            }
            DialogType.NEW_PASSWORD -> {
                val defaultPassword: String = stringResource(id = R.string.default_password)
                val currentPassword: String = stringResource(id = R.string.password)
                val title = if (state.passwordFormDs == DEFAULT_PASSWORD)
                    defaultPassword
                else
                    currentPassword

                // state.password is value to operation
                CommonDialog(
                    modifier = Modifier.fillMaxWidth(),
                    passwordValue = state.password,
                    dismissButtonLabel = stringResource(id = R.string.cancel),
                    dismissAction = {
                        viewModel.onAction(
                            HomeAction.ClickNewPasswordCancel
                        )
                    },
                    confirmButtonLabel = stringResource(id = R.string.OK),
                    confirmButtonType = ButtonType.SOLID_BUTTON,
                    confirmButtonAction = {
                        viewModel.onAction(HomeAction.ClickNewPasswordOk)
                    },
                    onPasswordValueChanged = {
                        viewModel.onAction(
                            HomeAction.PasswordValueChange(
                                passwordValueChange = it
                            )
                        )
                    },
                    isErrorPassword = state.isError,
                    keyboardAction = {},
                    title = title
                )
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            MoreActionSheetView(
                onItemClick = { mediaDetailSheetType ->
                    when (mediaDetailSheetType) {
                        MoreActionStatus.EDIT_NOTE.index -> {
                            scope.launch {
                                modalBottomSheetState.hide()
                            }
                            viewModel.onAction(
                                HomeAction.ClickEdit(user = state.editUser)
                            )
                            /* download()*/
                        }
                        MoreActionStatus.DELETE.index -> {
                            scope.launch {
                                modalBottomSheetState.hide()
                            }
                            viewModel.onAction(
                                HomeAction.ClickDelete(user = state.clearUser)
                            )
                            /*  vm.onActionImage(ImageAction.ClickReport)*/
                        }
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = MaterialTheme.dimen.base_2x,
            topEnd = MaterialTheme.dimen.base_2x,
        ),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(0.7f)
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                CommonTopBar(
                    topBarActionType = TopBarType.NoAction,
                    actionLabel = stringResource(id = R.string.new_password),
                    onActionLabelClicked = {
                        viewModel.onAction(HomeAction.ClickNewPassword)
                    },
                    title = stringResource(id = R.string.app_name)
                )
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
                    userVos = state.usersList,
                    onClickMoreAction = { userVo ->
                        viewModel.onAction(
                            HomeAction.ClickActionMore(userVo)
                        )
                    }
                )
            }
        )
    }
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
    userVos: List<UserVo> = emptyList(),
    onClickMoreAction: (userVo: UserVo) -> Unit,
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
                    onDeleteUser = { onDeleteUser(user) },
                    onClickMoreActionIcon = {
                        onClickMoreAction(user)
                    }
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
        HomeContent(onDeleteUser = {},
            onEditUser = {},
            onClickMoreAction = {})
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

