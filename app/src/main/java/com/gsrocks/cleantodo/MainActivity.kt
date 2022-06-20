package com.gsrocks.cleantodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gsrocks.cleantodo.core.presentation.navigation.Screen
import com.gsrocks.cleantodo.core.presentation.navigation.navigate
import com.gsrocks.cleantodo.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.gsrocks.cleantodo.feature_note.presentation.notes.NotesScreen
import com.gsrocks.cleantodo.ui.theme.CleanTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NoteAppContent() }
    }
}

@Composable
fun NoteAppContent() {
    CleanTodoTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.NotesScreen.route
            ) {
                composable(route = Screen.NotesScreen.route) {
                    NotesScreen(
                        onNavigate = {
                            navController.navigate(it)
                        }
                    )
                }
                composable(
                    route = Screen.AddEditNoteScreen.route +
                            "?${Screen.AddEditNoteScreen.argumentNoteId}={${Screen.AddEditNoteScreen.argumentNoteId}}" +
                            "&${Screen.AddEditNoteScreen.argumentNoteColor}={${Screen.AddEditNoteScreen.argumentNoteColor}}",
                    arguments = listOf(
                        navArgument(name = Screen.AddEditNoteScreen.argumentNoteId) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(name = Screen.AddEditNoteScreen.argumentNoteColor) {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    val color =
                        it.arguments?.getInt(Screen.AddEditNoteScreen.argumentNoteColor) ?: -1
                    AddEditNoteScreen(
                        onNavigateUp = { navController.navigateUp() },
                        noteColor = color
                    )
                }
            }
        }
    }
}
