package com.example.sidera_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.sidera_app.models.Question

class QuizViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private var allQuestionsCache = listOf<Question>()
    private val usedQuestionIds = mutableSetOf<String>()

    val questions = _questions.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    init {
        loadAllQuestions() // Precarga todas las preguntas al iniciar
    }

    private fun loadAllQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = db.collection("questions").get().await()
                allQuestionsCache = result.documents.mapNotNull { doc ->
                    val options = (doc.get("options") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

                    if (options.isNotEmpty() && doc.getString("question") != null) {
                        Question(
                            id = doc.id, // Asegúrate que tu modelo Question tenga un campo id
                            question = doc.getString("question")!!,
                            options = options,
                            correctIndex = doc.getLong("correctIndex")?.toInt() ?: 0,
                            category = doc.getString("category") ?: ""
                        )
                    } else {
                        null
                    }
                }
            } catch (e: Exception) {
                println("Error loading all questions: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRandomQuestions(count: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Si el cache está vacío, recargamos
                if (allQuestionsCache.isEmpty()) {
                    loadAllQuestions()
                }

                // Selección aleatoria simple
                _questions.value = if (allQuestionsCache.size <= count) {
                    allQuestionsCache.shuffled() // Si hay pocas preguntas, usa todas
                } else {
                    allQuestionsCache.shuffled().take(count)
                }

            } catch (e: Exception) {
                println("Error selecting random questions: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun resetQuiz() {
        _questions.value = emptyList()
    }

}