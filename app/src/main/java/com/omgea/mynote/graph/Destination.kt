package com.omgea.mynote.graph

sealed class Destination(
    val route: String
) {
    object Home : Destination("home")
    object Edit : Destination("edit?userId={userId}") {
        fun passId(userId: Int?): String {
            return "edit?userId=$userId"
        }
    }
}