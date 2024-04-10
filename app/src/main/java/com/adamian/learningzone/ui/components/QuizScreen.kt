import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.domain.model.QuestionItem

private val TAG = "QuizScreen"

@Composable
fun QuizScreen(questionItems: List<QuestionItem?>) {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedChoice by remember { mutableStateOf<Int?>(null) }
    var correctAnswers by remember { mutableStateOf(0) }
    var showCorrect by remember { mutableStateOf(false) }

    val questionItem = questionItems.getOrNull(currentIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (questionItem != null) {
            Text(
                text = questionItem.question,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            questionItem.options.forEachIndexed { index, choice ->
                ChoiceItem(
                    text = choice ?: "",
                    selected = index == selectedChoice,
                    correct = showCorrect && questionItem.correctOption == choice,
                    showCorrect = showCorrect, // Pass showCorrect here
                    onClick = {
                        // Only update the selected choice when the user clicks
                        if (!showCorrect) {
                            selectedChoice = index
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    Log.d(TAG, "QuizScreen: selectedChoice: $selectedChoice")
                    Log.d(TAG, "QuizScreen: showCorrect: $showCorrect")
                    if (selectedChoice != null && !showCorrect) {
                        if (selectedChoice == questionItem.options.indexOf(questionItem.correctOption)) {
                            correctAnswers++
                        }
                        showCorrect = true
                    } else {
                        currentIndex++
                        showCorrect = false
                        selectedChoice = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = if (showCorrect) "Next" else "Check")
            }
        } else {
            Text("Quiz Complete! You got $correctAnswers out of ${questionItems.size} correct.")
        }
    }
}

@Composable
fun ChoiceItem(
    text: String,
    selected: Boolean,
    correct: Boolean,
    onClick: () -> Unit,
    showCorrect: Boolean
) {
    val backgroundColor = if (showCorrect) {
        if (correct) Color.Green else Color.Red
    } else {
        if (selected) Color.Gray else Color.Transparent
    }
    val contentColor = if (selected || correct || showCorrect) Color.White else Color.Black

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = backgroundColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(backgroundColor)
                .fillMaxWidth(),
            color = contentColor
        )
    }
}
