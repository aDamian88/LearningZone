import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.domain.model.QuestionItem

@Composable
fun QuizScreen(questionItem: QuestionItem, onNextClick: () -> Unit) {
    var selectedChoice by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = questionItem.question,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        questionItem.options.forEachIndexed { index, choice ->
            ChoiceItem(
                text = choice!!,
                selected = index == selectedChoice,
                onClick = { selectedChoice = index }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
fun ChoiceItem(text: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (selected) Color.Gray else Color.Transparent
    val contentColor = if (selected) Color.White else Color.Black

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
