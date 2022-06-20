package com.gsrocks.cleantodo.feature_note.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gsrocks.cleantodo.MainActivity
import com.gsrocks.cleantodo.core.utils.TestTags
import com.gsrocks.cleantodo.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun saveNewNote_editAfterwards() {
        val testTitle = "Test title"
        val testContent = "Test content"
        val editedTitle = "2"
        with(composeRule) {
            onNodeWithContentDescription("Add note").performClick()

            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(testTitle)
            onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(testContent)
            onNodeWithContentDescription("Save note").performClick()

            onNodeWithText(testTitle).assertIsDisplayed()
            onNodeWithText(testTitle).performClick()

            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals(testTitle)
            onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals(testContent)
            onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(editedTitle)
            onNodeWithContentDescription("Save note").performClick()

            onNodeWithText(testTitle + editedTitle).assertIsDisplayed()
        }
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {
            with(composeRule) {
                onNodeWithContentDescription("Add note").performClick()

                onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(i.toString())
                onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(i.toString())
                onNodeWithContentDescription("Save note").performClick()
            }
        }

        for (i in 1..3) {
            composeRule.onNodeWithText(i.toString()).assertIsDisplayed()
        }

        with(composeRule) {
            onNodeWithContentDescription("Sort").performClick()
            onNodeWithContentDescription("Descending").performClick()

            for (i in 3 downTo 1) {
                onNodeWithText(i.toString()).assertIsDisplayed()
            }
        }
    }
}