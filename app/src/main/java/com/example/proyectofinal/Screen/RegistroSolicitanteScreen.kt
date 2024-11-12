package com.example.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

    var idUsuario by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedUsuario by remember { mutableStateOf<Usuario?>(null) }
    val usuarios = remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect(true) {
        usuarios.value = usuarioRepository.obtenerTodosUsuarios()
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
                        .height(120.dp)
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
                } },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                            .heightIn(max = 400.dp)
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
                            Text("ID Usuario:")
                            OutlinedTextField(
                                value = selectedUsuario?.let { "${it.nombres} ${it.apellidos}" } ?: "",
                                onValueChange = {},
                                label = { Text("Seleccionar Usuario") },
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { expanded = !expanded }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Desplegar opciones"
                                        )
                                    }
                                }
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                usuarios.value.forEach { usuario ->
                                    DropdownMenuItem(
                                        text = { Text("${usuario.nombres} ${usuario.apellidos}") },
                                        onClick = {
                                            selectedUsuario = usuario
                                            idUsuario = usuario.idUsuario
                                            expanded = false
                                        }
                                    )
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
                                onValueChange = { correo = it },
                                label = { Text("Correo") },
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
                                )
                            )

                            Button(
                                onClick = {
                                    // Verifica que todos los campos estén completos
                                    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                                        errorMessage = "Todos los campos son obligatorios."
                                    } else {
                                        idUsuario?.let { idUsuario ->
                                            scope.launch {
                                                onSaveEquipo(nombre.trim(), apellido.trim(), correo.trim(), telefono.trim(), idUsuario)
                                                nombre = ""
                                                apellido = ""
                                                correo = ""
                                                telefono = ""
                                                selectedUsuario = null
                                                errorMessage = "Equipo registrado con éxito."
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                            ) {
                                Text("Guardar", fontSize = 20.sp, color = Color.White)
                            }

                            // Mostrar mensaje de error o éxito si existe
                            if (errorMessage.isNotEmpty()) {
                                Text(
                                    text = errorMessage,
                                    color = if (errorMessage.contains("éxito")) Color.Green else Color.Red,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
