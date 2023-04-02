package com.gsrocks.cleantodo.feature_note.domain.model

import com.gsrocks.cleantodo.ui.theme.*

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
) {
    companion object {
        val noteColors = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}
