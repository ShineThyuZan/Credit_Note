package com.omgea.mynote.graph

object Routes {
    const val ROOT_ROUTE = "root"
    const val HOME = "notes"
}

sealed class Destination(
    val route: String
) {
    object Splash : Destination(route = "splash_screen")
    object Home : Destination("home")
    object Edit : Destination("edit?userId={userId}") {
        fun passId(userId: Int?): String {
            return "edit?userId=$userId"
        }
    }
    object CreateNewPassword : Destination("create_password")
}