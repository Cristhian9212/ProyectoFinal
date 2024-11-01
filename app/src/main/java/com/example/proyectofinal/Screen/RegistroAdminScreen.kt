package com.example.proyectofinal.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.proyectofinal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroAdminScreen(onRegister: (String, String, String, String, String, String) -> Unit) {
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var mostrarConfirmacionContrasena by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var fieldsValid by remember { mutableStateOf(true) }
    var emailValid by remember { mutableStateOf(true) }
    val complejidadContrasena = calcularComplejidadContrasena(contrasena)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Formulario de registro",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W900,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), // Separación desde el borde superior
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFA5D6A7))
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFA5D6A7), Color.White)
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
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
                        // Campos de entrada
                        OutlinedTextField(
                            value = nombres,
                            onValueChange = { nombres = it },
                            label = { Text("Nombres") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                        OutlinedTextField(
                            value = apellidos,
                            onValueChange = { apellidos = it },
                            label = { Text("Apellidos") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                        OutlinedTextField(
                            value = cargo,
                            onValueChange = { cargo = it },
                            label = { Text("Cargo") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                        OutlinedTextField(
                            value = nombreUsuario,
                            onValueChange = { nombreUsuario = it },
                            label = { Text("Nombre de Usuario") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                        OutlinedTextField(
                            value = correo,
                            onValueChange = {
                                correo = it
                                emailValid = validarCorreo(correo)
                            },
                            label = { Text("Correo") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                        if (!emailValid) {
                            Text(
                                text = "Formato de correo inválido",
                                color = Color.Red,
                                style = TextStyle(fontSize = 12.sp)
                            )
                        }
                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it },
                            label = { Text("Contraseña") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
                        ComplejidadBar(contrasena = contrasena, complejidad = complejidadContrasena)
                        OutlinedTextField(
                            value = confirmarContrasena,
                            onValueChange = { confirmarContrasena = it },
                            label = { Text("Confirmar Contraseña") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
                            style = TextStyle(fontSize = 12.sp)
                        )
                        Button(
                            onClick = {
                                fieldsValid = nombres.isNotEmpty() && apellidos.isNotEmpty() && nombreUsuario.isNotEmpty() &&
                                        correo.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()

                                if (fieldsValid && emailValid && contrasena == confirmarContrasena) {
                                    onRegister(nombres, apellidos, nombreUsuario, cargo, correo, contrasena)

                                    // Limpiar campos después del registro exitoso
                                    nombres = ""
                                    apellidos = ""
                                    cargo = ""
                                    nombreUsuario = ""
                                    correo = ""
                                    contrasena = ""
                                    confirmarContrasena = ""

                                    showConfirmationDialog = true
                                } else {
                                    showErrorDialog = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                        ) {
                            Text("Registrar")
                        }
                    }
                }
            }
        }

        // Diálogo de error
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text("Por favor, completa todos los campos correctamente.") },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // Diálogo de confirmación
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Registro Exitoso") },
                text = { Text("El usuario ha sido registrado correctamente.") },
                confirmButton = {
                    Button(onClick = { showConfirmationDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

// Función para validar el correo electrónico
fun validarCorreo(correo: String): Boolean {
    val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    return regex.matches(correo)
}
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
                        2 -> Color(0xFFFFA500)
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
                    2 -> Color(0xFFFFA500)
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
