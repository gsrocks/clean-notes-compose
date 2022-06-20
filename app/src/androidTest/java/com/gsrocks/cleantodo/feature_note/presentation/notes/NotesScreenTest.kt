package com.gsrocks.cleantodo.feature_note.presentation.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsrocks.cleantodo.MainActivity
import com.gsrocks.cleantodo.core.presentation.navigation.Screen
import com.gsrocks.cleantodo.core.presentation.navigation.navigate
import com.gsrocks.cleantodo.core.utils.TestTags
import com.gsrocks.cleantodo.di.AppModule
import com.gsrocks.cleantodo.ui.theme.CleanTodoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
       /* composeRule.setContent {
            val navController = rememberNavController()
            CleanTodoTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(Screen.NotesScreen.route) {
                        NotesScreen(onNavigate = { navController.navigate(it) })
                    }
                }
            }
        }*/
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}
