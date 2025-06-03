package com.example.sidera_app.models

data class Question(
    val id: String = "",                // ✔️ Usado para identificar la pregunta (coincide con Firebase ID)
    val question: String = "",          // ✔️ Texto de la pregunta
    val options: List<String> = emptyList(), // ✔️ Lista de opciones (debe tener al menos 2 elementos)
    val correctIndex: Int = 0,          // ✔️ Índice de la respuesta correcta (ej: 0 para la primera opción)
    val category: String = ""           // ✔️ Opcional: útil para filtrar por categorías luego
)