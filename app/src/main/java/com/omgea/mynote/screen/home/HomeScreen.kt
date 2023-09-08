package com.omgea.mynote.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.R
import com.omgea.mynote.common.ButtonType
import com.omgea.mynote.common.CommonDialog
import com.omgea.mynote.common.CommonTopBar
import com.omgea.mynote.common.TopBarType
import com.omgea.mynote.graph.Destination
import com.omgea.mynote.screen.home.components.DialogType
import com.omgea.mynote.screen.home.components.HomeAction
import com.omgea.mynote.screen.home.components.HomeEvent
import com.omgea.mynote.screen.home.components.HomeViewModel
import com.omgea.mynote.screen.home.sheet_view.HomeTopBar
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
    val listState = rememberLazyListState()
    val isScrolled = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

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
                    title = stringResource(id = R.string.note_list)
                )
            },
            floatingActionButton = {
                if (isScrolled.value) {

                } else {
                    HomeFab(
                        onFabClicked = { navController.navigate(Destination.Edit.route) }
                    )
                }
            },
            content = { innerPadding ->
                HomeContent(
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
                    },
                    listState = listState,
                    scaffoldPadding = innerPadding
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserContent() {
    MyNoteTheme(darkTheme = false) {
        HomeContent(
            onDeleteUser = {},
            onEditUser = {},
            onClickMoreAction = {},
            listState = LazyListState(
                firstVisibleItemIndex = 0,
                firstVisibleItemScrollOffset = 1
            ),
            scaffoldPadding = PaddingValues()
        )
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

