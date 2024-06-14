package com.adamian.learningzone.ui.navigation

sealed class NavRoute(val path: String) {

    object Home: NavRoute("home")
    object Login: NavRoute("login")
}