package com.example.test1.core.navigation

sealed class Routes(val value: String) {
    data object Login : Routes("login")
    data object Home : Routes("home")
}