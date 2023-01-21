package com.omgea.mynote.screen.create_new_password

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omgea.mynote.common.CommonTopBar
import com.omgea.mynote.common.TopBarType

import com.omgea.mynote.screen.home.components.CreatePasswordAction
import com.omgea.mynote.screen.home.components.HomeViewModel
import com.omgea.mynote.screen.home.components.PasswordTextFieldContent


@Composable
fun CreateNewPasswordScreen(
    navController: NavController
) {
    CreateNewPasswordView(
        navController = navController
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CreateNewPasswordView(
    navController: NavController
) {
    BackHandler {
        navController.popBackStack()
    }
    val vm: HomeViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordLength = vm.state.value.actionPassword
    var isActionLabelEnableState = true
    if(passwordLength.isEmpty()){
        isActionLabelEnableState = false
    }
    Scaffold(
        topBar = {
            CommonTopBar(
                navIcon = painterResource(id = com.omgea.mynote.R.drawable.ic_chevron_left),
                onNavIconClicked = {
                    navController.popBackStack()
                },
                title = stringResource(id = com.omgea.mynote.R.string.new_password),
                topBarActionType = TopBarType.NoAction,
                actionLabel = stringResource(id = com.omgea.mynote.R.string.OK),
                onActionLabelClicked = {
                    navController.popBackStack()
                },
                isEnableActionLabel = isActionLabelEnableState,
            )
        },
        content = {
            PasswordTextFieldContent(
                modifier = Modifier.padding(it),
                phoneNumber = vm.state.value.actionPassword.let {
                    vm.state.value.passwordFormDs
                },
                onPhoneValueChanged = { changePhoneValue ->
                    vm.onCreatePasswordAction(
                        CreatePasswordAction.ClickUpdatePassword(password = changePhoneValue.trim())
                    )

                },
                phonePlaceHolder = stringResource(id = com.omgea.mynote.R.string.new_password),
                phoneImeAction = ImeAction.Done,
                onPhoneValueCleared = {

                    vm.onCreatePasswordAction(
                        CreatePasswordAction.ClickUpdatePassword(password = "")
                    )
                },
                isErrorPhone = false,
                errorMessagePhone = stringResource(id = com.omgea.mynote.R.string.password),
                keyboardAction = {
                    keyboardController?.hide()
                },
            )
        }
    )
}
