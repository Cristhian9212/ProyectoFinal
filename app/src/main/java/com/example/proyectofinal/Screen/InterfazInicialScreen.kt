package com.example.proyectofinal.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterfazInicialScreen(onNavigate: () -> Unit) {
    // Contenido de tu pantalla
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Bienvenido a la Interfaz Inicial")

        Button(onClick = { onNavigate() }) {
            Text(text = "Volver a Login")
        }
    }
}

