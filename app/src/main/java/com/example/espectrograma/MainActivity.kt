package com.example.espectrograma

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.espectrograma.ui.theme.EspectrogramaTheme

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                // El usuario concedió el permiso.
            } else {
                // El usuario rechazó el permiso.
                // Más adelante podremos mostrar un mensaje.
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        checkAudioPermission()

        setContent {
            EspectrogramaTheme {
                App()
            }
        }
    }

    private fun checkAudioPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )

        }

    }

}