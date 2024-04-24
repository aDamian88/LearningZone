package com.adamian.learningzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.loginscreen.LoginView
import com.adamian.learningzone.ui.theme.LearningZoneTheme
import com.adamian.learningzone.ui.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val questionViewModel: QuestionViewModel by viewModels()

    private val questionItems = arrayOf(
        QuestionItem(
            "Για να αναπαραστήσουμε τα δεδομένα σε έναν αλγόριθμο χρησιμοποιούμε",
            "new description",
            listOf("Αριθμούς", "Λέξεις", "Σταθερές και μεταβλητές", "Αριθμούς και λέξεις"),
            "Σταθερές και μεταβλητές",
            1
        ),
        QuestionItem(
            "another question",
            "another description",
            listOf("first option", "second option", "third option", "fourthOption"),
            "second option",
            1
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // save questions
        questionItems.forEach {
            questionViewModel.saveQuestion(it)
        }
        setContent {
            LearningZoneTheme {
                val navController = rememberNavController()
                LoginView()
                }
            }
        }


}
