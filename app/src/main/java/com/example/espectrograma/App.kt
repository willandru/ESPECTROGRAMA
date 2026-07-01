package com.example.espectrograma

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {

    val navController = rememberNavController()

    Navegacion(navController)

}