package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
fun listarUsuarios(navController: NavController, usuarioRepository: UsuarioRepository) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var usuarios by remember { mutableStateOf(listOf<Usuario>()) }
    var usuarioAEditar by remember { mutableStateOf<Usuario?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        usuarios = usuarioRepository.obtenerTodosUsuarios()
    }

    BackHandler(enabled = drawerState.isClosed) {}

    val onNavigate: (String) -> Unit = { route ->
        navController.navigate(route)
        scope.launch { drawerState.close() }
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
                    DrawerContent(onNavigate)
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
                                text = "Inicio",
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val backgroundImage = painterResource(id = R.drawable.fondoprincipal)
                    Image(
                        painter = backgroundImage,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = 0.6f))
                    )

                    // Contenido en primer plano
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(usuarios) { usuario ->
                            var iconVisible by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Nombre: ${usuario.nombres} ${usuario.apellidos}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Cargo: ${usuario.cargo}",
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Correo: ${usuario.correo}",
                                        fontSize = 14.sp
                                    )

                                    // Fila para los iconos de editar y eliminar
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar",
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clickable {
                                                    usuarioAEditar = usuario
                                                    nombres = usuario.nombres
                                                    apellidos = usuario.apellidos
                                                    cargo = usuario.cargo
                                                    correo = usuario.correo
                                                    mostrarDialogoEditar = true
                                                },
                                            tint = Color.Blue
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clickable {
                                                    scope.launch {
                                                        usuarioRepository.eliminar(usuario)
                                                        usuarios = usuarioRepository.obtenerTodosUsuarios()
                                                    }
                                                },
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Dialogo para editar el usuario
                    if (mostrarDialogoEditar && usuarioAEditar != null) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoEditar = false },
                            title = { Text("Editar Usuario") },
                            text = {
                                Column {
                                    Text("Modifica los campos del usuario aquí")
                                    TextField(
                                        value = nombres,
                                        onValueChange = { nombres = it },
                                        label = { Text("Nombres") }
                                    )
                                    TextField(
                                        value = apellidos,
                                        onValueChange = { apellidos = it },
                                        label = { Text("Apellidos") }
                                    )
                                    TextField(
                                        value = cargo,
                                        onValueChange = { cargo = it },
                                        label = { Text("Cargo") }
                                    )
                                    TextField(
                                        value = correo,
                                        onValueChange = { correo = it },
                                        label = { Text("Correo") }
                                    )
                                }
                            },
                            confirmButton = {
                                Button(onClick = {
                                    // Lógica para guardar cambios en el usuario
                                    val usuarioModificado = usuarioAEditar?.copy(
                                        nombres = nombres,
                                        apellidos = apellidos,
                                        cargo = cargo,
                                        correo = correo
                                    )
                                    usuarioModificado?.let {
                                        scope.launch {
                                            usuarioRepository.actualizar(it)
                                            usuarios = usuarioRepository.obtenerTodosUsuarios()
                                        }
                                    }
                                    mostrarDialogoEditar = false
                                }) {
                                    Text("Guardar")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { mostrarDialogoEditar = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}
