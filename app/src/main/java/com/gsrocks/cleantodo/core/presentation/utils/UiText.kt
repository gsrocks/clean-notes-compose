package com.gsrocks.cleantodo.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val text: String) : UiText()
    data class StringResource(@StringRes val resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}