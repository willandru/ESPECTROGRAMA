package com.example.espectrograma

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navegacion(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "principal"
    ) {

        composable("principal") {
            PantallaPrincipal(navController)
        }

        composable("espectrograma") {
            PantallaEspectrograma(navController)
        }

    }

}