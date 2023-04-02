package com.gsrocks.cleantodo.core.presentation.navigation

import com.gsrocks.cleantodo.core.presentation.utils.UiText

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}
