package com.example.sidera_app

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

@Composable
fun LessonScreen() {
    var currentIndex by remember { mutableStateOf(0) }
    var datosOriginales by remember { mutableStateOf<List<ApodResponse>>(emptyList()) }
    var datosTraducidos by remember { mutableStateOf<Map<Int, ApodResponse>>(emptyMap()) }
    var cargando by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Carga inicial
    LaunchedEffect(Unit) {
        try {
            cargando = true
            errorMessage = null
            val respuesta = NasaApi.service.getApods()
            val filtradas = respuesta.filter { !it.displayUrl.isNullOrBlank() }

            if (filtradas.isEmpty()) {
                errorMessage = "No se recibieron imágenes disponibles desde la NASA."
            }

            datosOriginales = filtradas
        } catch (e: Exception) {
            Log.e("LessonScreen", "Error al cargar datos: ${e.message}")
            errorMessage = "No se pudieron descargar los datos de la NASA."
        } finally {
            cargando = false
        }
    }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (datosOriginales.isNotEmpty()) {
        val actual = datosTraducidos[currentIndex] ?: datosOriginales[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = actual.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )

                val imagen = actual.displayUrl
                if (!imagen.isNullOrBlank()) {
                    AsyncImage(
                        model = imagen,
                        contentDescription = actual.title,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Contenido multimedia no disponible",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = actual.explanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,  // blanco
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botones de navegación y traducción ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { if (currentIndex > 0) currentIndex-- },
                    enabled = currentIndex > 0
                ) {
                    Text("Anterior")
                }

                Button(onClick = {
                    coroutineScope.launch {
                        val original = datosOriginales[currentIndex]
                        val tituloTraducido = async { traducirTexto(original.title) }
                        val explicacionTraducida = async { traducirTexto(original.explanation) }

                        datosTraducidos = datosTraducidos + (currentIndex to original.copy(
                            title = tituloTraducido.await(),
                            explanation = explicacionTraducida.await()
                        ))
                    }
                }) {
                    Text("Traducir")
                }

                Button(
                    onClick = { if (currentIndex < datosOriginales.size - 1) currentIndex++ },
                    enabled = currentIndex < datosOriginales.size - 1
                ) {
                    Text("Siguiente")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { /* Acción futura */ }) {
                Text("Saber más")
            }

            errorMessage?.let { mensaje ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = Color(0xFFFFC107),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage ?: "No se pudieron cargar los datos.")
        }
    }
}

// --- Función de traducción ---
suspend fun traducirTexto(texto: String): String {
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.SPANISH)
        .build()
    val translator = Translation.getClient(options)

    return try {
        translator.downloadModelIfNeeded(DownloadConditions.Builder().build()).await()
        translator.translate(texto).await()
    } catch (e: Exception) {
        Log.e("Traducción", "Error al traducir: ${e.localizedMessage}")
        texto
    } finally {
        translator.close()
    }
}
