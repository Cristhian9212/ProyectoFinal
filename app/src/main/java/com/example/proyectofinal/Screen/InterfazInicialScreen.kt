package com.example.proyectofinal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterfazInicialScreen(onNavigate: () -> Unit) {
    // Contenido de tu pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Fondo blanco para la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centrar verticalmente el contenido
    ) {
        Text(
            text = "Bienvenido a la Interfaz Inicial",
            style = MaterialTheme.typography.titleLarge // Estilo de texto grande
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre el texto y el botón

        Button(
            onClick = { onNavigate() }, // Acción al hacer clic en el botón
            modifier = Modifier.padding(top = 16.dp) // Espaciado adicional arriba del botón
        ) {
            Text(text = "Volver a Login")
        }
    }
}
