package com.example.sidera_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sidera_app.models.Question
import com.example.sidera_app.viewmodels.QuizViewModel

@Composable
fun QuizScreen() {
    val viewModel: QuizViewModel = viewModel()
    val questions by viewModel.questions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var quizStarted by remember { mutableStateOf(false) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0B21))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        when {
            // Pantalla de inicio
            !quizStarted -> StartQuizScreen {
                quizStarted = true
                currentQuestionIndex = 0
                score = 0
                viewModel.loadRandomQuestions(5)
            }

            // Pantalla de carga
            isLoading -> LoadingScreen()

            // Quiz en progreso
            questions.isNotEmpty() && currentQuestionIndex < questions.size -> {
                val currentQuestion = questions[currentQuestionIndex]
                QuestionLayout(
                    question = currentQuestion,
                    currentQuestionIndex = currentQuestionIndex,
                    totalQuestions = questions.size,
                    onAnswerSelected = { selectedIndex ->
                        if (selectedIndex == currentQuestion.correctIndex) {
                            score += 10
                        }
                        currentQuestionIndex++
                    },
                    score = score
                )
            }

            // Quiz completado
            questions.isNotEmpty() -> {
                QuizCompletedScreen(
                    score = score,
                    onRestart = {
                        quizStarted = false
                        viewModel.resetQuiz()
                    }
                )
            }
        }
    }
}

@Composable
private fun StartQuizScreen(onStartQuiz: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Bienvenido al Quiz Astronómico",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartQuiz,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA000),
                contentColor = Color.Black
            )
        ) {
            Text("Realizar Quiz (5 preguntas)")
        }
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color(0xFFFFA000))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cargando preguntas...",
            color = Color.White
        )
    }
}

@Composable
private fun QuestionLayout(
    question: Question,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onAnswerSelected: (Int) -> Unit,
    score: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Indicador de progreso (ej. "Pregunta 1/5")
        Text(
            text = "${currentQuestionIndex + 1}/$totalQuestions",
            color = Color(0xFFFFA000),
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        question.options.forEachIndexed { index, option ->
            Button(
                onClick = { onAnswerSelected(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E1A3A),
                    contentColor = Color.White
                )
            ) {
                Text(option)
            }
        }

        Text(
            text = "Puntuación: $score",
            color = Color(0xFFFFA000),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun QuizCompletedScreen(
    score: Int,
    onRestart: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.QuestionAnswer,
            contentDescription = "Quiz completado",
            tint = Color(0xFFFFA000),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "¡Quiz completado!",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Puntuación final: $score",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFFFA000)
        )
        Button(
            onClick = onRestart,
            modifier = Modifier.padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA000),
                contentColor = Color.Black
            )
        ) {
            Text("Volver al inicio")
        }
    }
}