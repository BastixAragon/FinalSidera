package com.example.sidera_app

import android.content.Context
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.filament.*
import com.google.android.filament.utils.Float3
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils
import com.google.android.filament.utils.rotation
import android.view.Choreographer
import java.nio.ByteBuffer

@Composable
fun SolarSystemViewer() {
    val context = LocalContext.current

    // Creamos una vista Android vacía que no hace nada
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            // Comentamos toda la inicialización de Filament y ModelViewer
            /*
            Utils.init()

            SurfaceView(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )

                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()

                val modelViewer = ModelViewer(this)

                holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        Log.d("SolarSystemViewer", "Surface creada, iniciando carga del modelo...")
                        modelViewer.setupScene()
                        modelViewer.loadModelGlb(context, "earth.glb")
                        modelViewer.startRenderLoop()
                    }

                    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                        Log.d("SolarSystemViewer", "Surface cambiada: $width x $height")
                        modelViewer.view.viewport = Viewport(0, 0, width, height)
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        Log.d("SolarSystemViewer", "Surface destruida")
                        modelViewer.destroyModel()
                    }
                })
            }
            */

            // En su lugar, simplemente devolvemos una SurfaceView vacía
            SurfaceView(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )
}

/*
// Comentamos todas las funciones auxiliares ya que no se usarán
private fun ModelViewer.setupScene() {
    camera.lookAt(
        0.0, 0.0, 1.5,  // posición de la cámara (más cerca)
        0.0, 0.0, 0.0,  // hacia el centro
        0.0, 1.0, 0.0   // arriba
    )

    scene.skybox = Skybox.Builder().build(engine)

    scene.indirectLight = IndirectLight.Builder()
        .intensity(30_000f) // reduce un poco
        .build(engine)

    val sunlight = EntityManager.get().create()
    LightManager.Builder(LightManager.Type.DIRECTIONAL)
        .color(1.0f, 1.0f, 1.0f)
        .intensity(100_000f)
        .direction(0.0f, -1.0f, -1.0f)
        .castShadows(true)
        .build(engine, sunlight)

    scene.addEntity(sunlight)
}

private fun ModelViewer.loadModelGlb(ctx: Context, assetName: String) {
    try {
        ctx.assets.open("models/$assetName").use { input ->
            val bytes = input.readBytes()
            val buffer = ByteBuffer.wrap(bytes)

            loadModelGlb(buffer)
            transformToUnitCube()

            Log.d("SolarSystemViewer", "Modelo '$assetName' cargado correctamente.")
        }
    } catch (e: Exception) {
        Log.e("SolarSystemViewer", "Error al cargar modelo '$assetName': ${e.localizedMessage}")
    }
}

private fun ModelViewer.startRenderLoop() {
    val rotationSpeed = 0.5f
    var angle = 0f

    val choreographer = Choreographer.getInstance()

    val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            asset?.let {
                val transform = rotation(Float3(0f, 1f, 0f), angle)
                val tm = engine.transformManager
                val instance = tm.getInstance(it.root)
                tm.setTransform(instance, transform.toFloatArray())
            }

            render(frameTimeNanos)

            angle = (angle + rotationSpeed) % 360f

            choreographer.postFrameCallback(this)
        }
    }

    choreographer.postFrameCallback(frameCallback)
}
*/