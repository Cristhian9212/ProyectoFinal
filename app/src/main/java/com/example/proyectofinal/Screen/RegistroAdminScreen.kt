package com.example.proyectofinal.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.example.proyectofinal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroAdminScreen(onRegister: (String, String, String) -> Unit) {
    // Variables para almacenar los datos del formulario
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) } // Estado para mostrar/ocultar la contraseña

    // Contenido de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Administradores") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFA5D6A7))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFA5D6A7), Color.White)
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Campo para el nombre de usuario
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
                    focusedBorderColor = Color(0xFF388E3C),
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                )
            )
            // Campo para la contraseña
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
                    focusedBorderColor = Color(0xFF388E3C),
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    val icon = if (mostrarContrasena) {
                        painterResource(id = R.drawable.on) // Icono para mostrar
                    } else {
                        painterResource(id = R.drawable.off) // Icono para ocultar
                    }
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp)) // Modificar el tamaño aquí
                    }
                }
            )
            // Campo para el correo
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
                    focusedBorderColor = Color(0xFF388E3C),
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Botón para registrar
            Button(
                onClick = {
                    // Llama a la función onRegister al hacer clic en el botón
                    onRegister(nombreUsuario, contrasena, correo)
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
