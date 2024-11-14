package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.UsuarioRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroSolicitanteScreen(
    navController: NavController,
    usuarioRepository: UsuarioRepository,
    onSaveEquipo: suspend (String, String, String, String, Int) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Variables de estado para los campos y mensajes de validación
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // Para los mensajes de error o éxito
    var correoError by remember { mutableStateOf("") }

// Expresión regular para validar el formato de correo electrónico
    val correoRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    var idUsuario by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedUsuario by remember { mutableStateOf<Usuario?>(null) }
    val usuarios = remember { mutableStateOf<List<Usuario>>(emptyList()) }

    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        // Navega hacia la pantalla inicial cuando el usuario retrocede
        navController.navigate("interfaz_inicial") {
            popUpTo("interfaz_inicial") { inclusive = true }
        }
    }
    LaunchedEffect(true) {
        usuarios.value = usuarioRepository.obtenerTodosUsuarios()
    }

    // Mostrar el mensaje en la notificación temporal `Snackbar`
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    ModalNavigationDrawer(
        drawerContent = { if (drawerState.isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Color.White)
            ) {
                DrawerContent { route ->
                    navController.navigate(route)
                    scope.launch { drawerState.close() }
                }
            }
        } },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.iniciosup),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )

                    TopAppBar(
                        title = {
                            Text(
                                text = "Registro de estudiantes",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                                    .padding(6.dp)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir menú",
                                    tint = Color.Black,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }},
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(0.9f)
                            .height(70.dp),
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Imagen personalizada
                                Image(
                                    painter = painterResource(id = R.drawable.udec),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))

                                // Texto del Snackbar
                                Text(
                                    text = data.visuals.message,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )
                }
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.registrar),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.TopCenter)
                                .offset(y = (-10).dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                            .heightIn(max = 600.dp)
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(top = 4.dp)
                                    .background(Color.Transparent)
                                    .border(BorderStroke(1.dp, Color(0xFF4CAF50)), shape = MaterialTheme.shapes.small)
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable { expanded = true } // Manejo del estado de expansión
                                    .padding(vertical = 10.dp)
                            ) {
                                // Texto para el usuario seleccionado o texto por defecto si no se ha seleccionado ninguno
                                Text(
                                    text = selectedUsuario?.let { "${it.nombres} ${it.apellidos}" } ?: "Seleccionar Usuario",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                                )

                                // DropdownMenu para desplegar las opciones de usuarios
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .fillMaxWidth() // Asegura que el DropdownMenu tenga el mismo ancho que el Box
                                        .background(Color.White)
                                        .offset(y = 4.dp) // Desplaza el menú justo debajo del Box sin espacios grandes
                                ) {
                                    // Iterar sobre la lista de usuarios para mostrar cada uno como opción
                                    usuarios.value.forEach { usuario ->
                                        DropdownMenuItem(
                                            text = { Text("${usuario.nombres} ${usuario.apellidos}") },
                                            onClick = {
                                                selectedUsuario = usuario
                                                idUsuario = usuario.idUsuario
                                                expanded = false // Cerrar menú después de seleccionar
                                            }
                                        )
                                    }
                                }
                            }

                            TextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color(0xFF4CAF50),
                                    unfocusedLabelColor = Color.Gray,
                                    containerColor = Color.Transparent,
                                    cursorColor = Color(0xFF4CAF50)
                                )
                            )

                            TextField(
                                value = apellido,
                                onValueChange = { apellido = it },
                                label = { Text("Apellido") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color(0xFF4CAF50),
                                    unfocusedLabelColor = Color.Gray,
                                    containerColor = Color.Transparent,
                                    cursorColor = Color(0xFF4CAF50)
                                )
                            )

                            TextField(
                                value = correo,
                                onValueChange = {
                                    correo = it
                                    correoError = if (correoRegex.matches(it)) "" else "Correo no válido"
                                },
                                label = { Text("Correo") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                    .border(1.dp, if (correoError.isEmpty()) Color(0xFF4CAF50) else Color.Red, RoundedCornerShape(8.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = if (correoError.isEmpty()) Color(0xFF4CAF50) else Color.Red,
                                    unfocusedLabelColor = Color.Gray,
                                    containerColor = Color.Transparent,
                                    cursorColor = Color(0xFF4CAF50)
                                ),
                                isError = correoError.isNotEmpty()
                            )

// Mostrar mensaje de error si el correo es inválido
                            if (correoError.isNotEmpty()) {
                                Text(
                                    text = correoError,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            TextField(
                                value = telefono,
                                onValueChange = { telefono = it },
                                label = { Text("Teléfono") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp)),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color(0xFF4CAF50),
                                    unfocusedLabelColor = Color.Gray,
                                    containerColor = Color.Transparent,
                                    cursorColor = Color(0xFF4CAF50)
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Teclado numérico
                            )


                            Button(
                                onClick = {
                                    // Verifica que todos los campos estén completos y que el correo sea válido
                                    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                                        errorMessage = "Todos los campos son obligatorios."
                                    } else if (correoError.isNotEmpty()) {
                                        // Mensaje de error específico para correo no válido
                                        errorMessage = "Por favor, ingresa un correo válido."
                                    } else {
                                        // Guardar sólo si no hay error en el correo
                                        idUsuario?.let { idUsuario ->
                                            scope.launch {
                                                onSaveEquipo(nombre.trim(), apellido.trim(), correo.trim(), telefono.trim(), idUsuario)
                                                nombre = ""
                                                apellido = ""
                                                correo = ""
                                                telefono = ""
                                                selectedUsuario = null
                                                errorMessage = "Estudiante registrado con éxito."
                                            }
                                        }
                                    }
                                },
                                enabled = correoError.isEmpty(), // Desactiva el botón si el correo es inválido
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (correoError.isEmpty()) Color(0xFF4CAF50) else Color.Gray // Cambia el color si está deshabilitado
                                )
                            ) {
                                Text("Guardar", fontSize = 20.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        )
    }
}
