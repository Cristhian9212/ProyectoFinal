package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import android.os.Process
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    usuarioRepository: UsuarioRepository,
    coroutineScope: CoroutineScope,
    onLoginSuccess: (Usuario) -> Unit,
    navController: NavController
) {
    var usuario by remember { mutableStateOf(TextFieldValue("")) }
    var contrasena by remember { mutableStateOf(TextFieldValue("")) }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") } // Estado para el mensaje de error
    BackHandler {
        // Finaliza la aplicación al presionar el botón de retroceso
        Process.killProcess(Process.myPid())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFA5D6A7), Color.White)
                    )
                )
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Image(
                painter = painterResource(id = R.drawable.udec),
                contentDescription = "Descripción de la imagen",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "COMPUSHARE",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.redhat)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            color = Color(0xFF020202)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Nombre de Usuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF81C784),
                            unfocusedBorderColor = Color(0xFF81C784),
                            cursorColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF81C784),
                            unfocusedBorderColor = Color(0xFF81C784),
                            cursorColor = Color.Black
                        ),
                        trailingIcon = {
                            IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                                Icon(
                                    painter = painterResource(id = if (contrasenaVisible) R.drawable.on else R.drawable.off),
                                    contentDescription = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Mostrar el mensaje de error si existe
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            // Validación de campos
                            if (usuario.text.isNotEmpty() && contrasena.text.isNotEmpty()) {
                                coroutineScope.launch {
                                    val usuarioResult = usuarioRepository.login(usuario.text, contrasena.text)
                                    if (usuarioResult != null) {
                                        onLoginSuccess(usuarioResult)
                                        navController.navigate("interfaz_inicial") {
                                            popUpTo("login") { inclusive = true } // Esto eliminará la pantalla de inicio de sesión del historial de navegación
                                            launchSingleTop = true // Evita duplicados de la pantalla principal en el stack
                                        }
                                    } else {
                                        errorMessage = "Credenciales incorrectas"
                                    }
                                }
                            } else {
                                errorMessage = "Por favor, ingrese usuario y contraseña" // Mensaje si los campos están vacíos
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF388E3C),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Login")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ClickableText(
                        text = AnnotatedString("¿No tienes una cuenta? Regístrate"),
                        onClick = {
                            navController.navigate("registro")
                        },
                        style = TextStyle(
                            color = Color(0xFF388E3C),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}