package com.gsrocks.cleantodo.core.presentation.navigation

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}