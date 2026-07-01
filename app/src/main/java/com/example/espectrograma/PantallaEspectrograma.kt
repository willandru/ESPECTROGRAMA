package com.example.espectrograma

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PantallaEspectrograma(
    navController: NavController
) {

    val context = LocalContext.current

    val recorder = remember {
        AudioRecorder(context)
    }

    val samplesState = remember {
        mutableStateOf(ShortArray(0))
    }

    val mainHandler = remember {
        Handler(Looper.getMainLooper())
    }

    DisposableEffect(Unit) {

        recorder.start { samples ->

            mainHandler.post {
                samplesState.value = samples
            }

        }

        onDispose {
            recorder.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text("Onda de audio")

        WaveformView(
            samples = samplesState.value
        )

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Volver")
        }

    }

}