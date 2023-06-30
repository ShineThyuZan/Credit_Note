package com.omgea.mynote.graph

import android.annotation.SuppressLint
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.omgea.mynote.screen.edit.NoteEditScreen
import com.omgea.mynote.screen.create_new_password.CreateNewPasswordScreen
import com.omgea.mynote.screen.home.HomeScreen
@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.navGraph(
    navController: NavController
) {
    navigation(
        startDestination = Destination.Home.route,
        route = Routes.HOME
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Destination.Edit.route,
            arguments = listOf(
                navArgument(
                    name = "userId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            NoteEditScreen(navController = navController)
        }
        composable(route = Destination.CreateNewPassword.route) {
            CreateNewPasswordScreen(navController = navController)
        }
    }
}