package com.example.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    onSaveEquipo: suspend (String, String, String, String, Int) -> Unit // Cambiar aquí a suspend
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Variables de estado para cada campo del formulario
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Variables para el idUsuario
    var idUsuario by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedUsuario by remember { mutableStateOf<Usuario?>(null) }

    // Lista de usuarios que se actualizará una vez obtenida
    val usuarios = remember { mutableStateOf<List<Usuario>>(emptyList()) }

    // Llamada suspendida para obtener los usuarios
    LaunchedEffect(true) {
        // Obtener usuarios dentro de la corrutina
        usuarios.value = usuarioRepository.obtenerTodosUsuarios()
    }

    // Mostrar idUsuario cuando esté seleccionado
    val idUsuarioSeleccionado = selectedUsuario?.idUsuario

    ModalNavigationDrawer(
        drawerContent = {
            if (drawerState.isOpen) {
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
            }
        },
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
                        painter = painterResource(id = R.drawable.fondo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )

                    TopAppBar(
                        title = {
                            Text(
                                text = "Registro de solicitantes",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir menú",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
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
                    // Dropdown para seleccionar idUsuario
                    Text("ID Usuario:")
                    OutlinedTextField(
                        value = selectedUsuario?.let { "${it.nombres} ${it.apellidos}" }?: "",
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
                        // Mostrar lista de usuarios solo si está disponible
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

                    Spacer(modifier = Modifier.height(20.dp))

                    // Formulario de registro
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            idUsuario?.let { idUsuario ->
                                // Llamada para guardar los datos
                                scope.launch {
                                    onSaveEquipo(nombre, apellido, correo, telefono, idUsuario)
                                    // Limpiar los campos después de guardar
                                    nombre = ""
                                    apellido = ""
                                    correo = ""
                                    telefono = ""
                                    selectedUsuario = null

                                }
                            }
                        }
                    ) {
                        Text("Guardar Solicitante")
                    }
                }
            }
        )
    }
}