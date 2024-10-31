package com.example.proyectofinal.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.proyectofinal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroAdminScreen(onRegister: (String, String, String) -> Unit) {
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var mostrarConfirmacionContrasena by remember { mutableStateOf(false) }

    val complejidadContrasena = calcularComplejidadContrasena(contrasena)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Administradores") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFA5D6A7))
            )
        }
    ) { padding ->
        // Ajustar el padding inferior dinámicamente según la aparición del teclado
        val view = LocalView.current
        val density = LocalDensity.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = with(density) { LocalContext.current.resources.configuration.keyboard != null && LocalContext.current.resources.configuration.keyboard > 0 }.let { if (it) 16.dp else 0.dp }) // Padding inferior para el teclado
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFA5D6A7), Color.White)
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = nombreUsuario,
                        onValueChange = { nombreUsuario = it },
                        label = { Text("Nombre de Usuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        trailingIcon = {
                            val icon = if (mostrarContrasena) {
                                painterResource(id = R.drawable.on)
                            } else {
                                painterResource(id = R.drawable.off)
                            }
                            IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                                Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp))
                            }
                        }
                    )

                    // Solo se muestra la barra de complejidad para la contraseña
                    ComplejidadBar(contrasena = contrasena, complejidad = complejidadContrasena)

                    OutlinedTextField(
                        value = confirmarContrasena,
                        onValueChange = { confirmarContrasena = it },
                        label = { Text("Confirmar Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        visualTransformation = if (mostrarConfirmacionContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        trailingIcon = {
                            val icon = if (mostrarConfirmacionContrasena) {
                                painterResource(id = R.drawable.on)
                            } else {
                                painterResource(id = R.drawable.off)
                            }
                            IconButton(onClick = { mostrarConfirmacionContrasena = !mostrarConfirmacionContrasena }) {
                                Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp))
                            }
                        }
                    )

                    // Mostrar mensaje de coincidencia de contraseñas
                    Text(
                        text = if (confirmarContrasena.isNotEmpty()) {
                            if (contrasena == confirmarContrasena) {
                                "Las contraseñas coinciden"
                            } else {
                                "Las contraseñas no coinciden"
                            }
                        } else {
                            ""
                        },
                        color = if (contrasena == confirmarContrasena) Color.Black else Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (contrasena == confirmarContrasena) {
                                onRegister(nombreUsuario, contrasena, correo)
                            } else {
                                // Manejar error si las contraseñas no coinciden
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF388E3C),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Registrar",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

// ... El resto de tu código sigue igual ...


@Composable
fun ComplejidadBar(contrasena: String, complejidad: Int) {
    if (contrasena.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            // Texto descriptivo alineado a la izquierda
            Text(
                text = when (complejidad) {
                    1 -> "Bajo"
                    2 -> "Medio"
                    3 -> "Alto"
                    else -> ""
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = when (complejidad) {
                        1 -> Color.Red
                        2 -> Color.Yellow
                        3 -> Color.Green
                        else -> Color.Transparent
                    }
                ),
                modifier = Modifier.weight(1f)
            )

            // Barra de progreso a la derecha del texto
            LinearProgressIndicator(
                progress = complejidad / 3f,
                color = when (complejidad) {
                    1 -> Color.Red
                    2 -> Color.Yellow
                    3 -> Color.Green
                    else -> Color.Transparent
                },
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(0.5f)
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
            )
        }
    }
}

fun calcularComplejidadContrasena(contrasena: String): Int {
    var score = 0
    if (contrasena.length >= 8) score++ // Longitud mínima
    if (contrasena.any { it.isDigit() }) score++ // Al menos un dígito
    if (contrasena.any { !it.isLetterOrDigit() }) score++ // Al menos un carácter especial

    return when (score) {
        0 -> 0 // Sin complejidad
        1 -> 1 // Bajo
        2 -> 2 // Medio
        3 -> 3 // Alto
        else -> 0 // Por si acaso
    }
}
