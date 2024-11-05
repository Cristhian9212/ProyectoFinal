package com.example.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Repository.UsuarioRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroSolicitanteScreen(
    navController: NavController,
    usuarioRepository: UsuarioRepository,
    onSaveEquipo: (String, String, String, String, Int) -> Unit // Añadimos `creadoPor` como parámetro
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Variables de estado para cada campo del formulario
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Variables de estado para el login del usuario
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var idUsuario by remember { mutableStateOf<Int?>(null) }

    // Lógica para autenticar usuario y obtener `creadoPor`
    LaunchedEffect(Unit) {
        val usuario = usuarioRepository.login(nombreUsuario, contrasena)
        idUsuario = usuario?.idUsuario
    }

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
                    // Login Form
                    OutlinedTextField(
                        value = nombreUsuario,
                        onValueChange = { nombreUsuario = it },
                        label = { Text("Nombre de Usuario") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val usuario = usuarioRepository.login(nombreUsuario, contrasena)
                                idUsuario = usuario?.idUsuario
                            }
                        }
                    ) {
                        Text("Iniciar Sesión")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Registro Form
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
                                onSaveEquipo(nombre, apellido, correo, telefono, idUsuario)
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