package com.omgea.mynote.graph

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omgea.mynote.screen.edit.EditScreen
import com.omgea.mynote.screen.home.CreateNewPasswordScreen
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
            EditScreen(navController = navController)
        }
        composable(route = Destination.CreateNewPassword.route) {
            CreateNewPasswordScreen(navController = navController)
        }
    }
}