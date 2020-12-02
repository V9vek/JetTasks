package com.vivek.jettasks.ui.screens

sealed class Routing(val route: String, val label: String) {

    object Main: Routing(route = "main", label = "Main")
    object Edit: Routing(route = "edit", label = "Edit")
}