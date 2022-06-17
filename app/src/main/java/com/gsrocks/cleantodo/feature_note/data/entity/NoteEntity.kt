package com.gsrocks.cleantodo.feature_note.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gsrocks.cleantodo.feature_note.domain.model.Note

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
) {
    fun toNote() = Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )

    companion object {
        fun fromNote(note: Note) = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            color = note.color
        )
    }
}
