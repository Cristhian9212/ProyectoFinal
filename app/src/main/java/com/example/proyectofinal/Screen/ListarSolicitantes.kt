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
import com.example.proyectofinal.Model.SolicitanteConUsuario
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.SolicitanteRepository
import com.example.proyectofinal.Repository.UsuarioRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarSolicitantes(
    navController: NavController,
    solicitanteRepository: SolicitanteRepository,
    usuarioRepository: UsuarioRepository
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var solicitantesConUsuarios by remember { mutableStateOf(listOf<SolicitanteConUsuario>()) }
    var usuariosDisponibles by remember { mutableStateOf(listOf<Usuario>()) } // Lista de usuarios para el Dropdown
    var solicitanteAEditar by remember { mutableStateOf<SolicitanteConUsuario?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var idUsuarioSeleccionado by remember { mutableStateOf<Int?>(null) } // ID seleccionado en el Dropdown

    // Cargar los solicitantes y usuarios disponibles
    LaunchedEffect(Unit) {
        solicitantesConUsuarios = solicitanteRepository.obtenerSolicitantesConUsuarios()
        usuariosDisponibles = usuarioRepository.obtenerTodosUsuarios()// Asume que esto devuelve la lista de usuarios
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
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    TopAppBar(
                        title = {
                            Text(
                                text = "Solicitantes",
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.fondoprincipal),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = 0.6f))
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(solicitantesConUsuarios) { solicitanteConUsuario ->
                            val solicitante = solicitanteConUsuario.solicitante
                            val usuario = solicitanteConUsuario.usuario

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
                                        text = "Nombre: ${solicitante.nombre} ${solicitante.apellido}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Correo: ${solicitante.correo}",
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Teléfono: ${solicitante.telefono}",
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Usuario Asignado: ${usuario.nombres} ${usuario.apellidos}",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )

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
                                                .size(24.dp)
                                                .clickable {
                                                    solicitanteAEditar = solicitanteConUsuario
                                                    nombre = solicitante.nombre
                                                    apellido = solicitante.apellido
                                                    correo = solicitante.correo
                                                    telefono = solicitante.telefono
                                                    idUsuarioSeleccionado = usuario.idUsuario // Asignar el idUsuario actual
                                                    mostrarDialogoEditar = true
                                                },
                                            tint = Color.Blue
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    scope.launch {
                                                        solicitanteRepository.eliminar(solicitante)
                                                        solicitantesConUsuarios = solicitanteRepository.obtenerSolicitantesConUsuarios()
                                                    }
                                                },
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (mostrarDialogoEditar && solicitanteAEditar != null) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialogoEditar = false },
                            title = { Text("Editar Solicitante") },
                            text = {
                                Column {
                                    Text("Modifica los campos del solicitante aquí")
                                    TextField(
                                        value = nombre,
                                        onValueChange = { nombre = it },
                                        label = { Text("Nombre") }
                                    )
                                    TextField(
                                        value = apellido,
                                        onValueChange = { apellido = it },
                                        label = { Text("Apellido") }
                                    )
                                    TextField(
                                        value = correo,
                                        onValueChange = { correo = it },
                                        label = { Text("Correo") }
                                    )
                                    TextField(
                                        value = telefono,
                                        onValueChange = { telefono = it },
                                        label = { Text("Teléfono") }
                                    )
                                    // Dropdown menu para seleccionar usuario
                                    var expanded by remember { mutableStateOf(false) }
                                    Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                                        Text(
                                            text = usuariosDisponibles.find { it.idUsuario == idUsuarioSeleccionado }
                                                ?.let { "${it.nombres} ${it.apellidos}" } ?: "Seleccionar Usuario",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { expanded = true }
                                                .padding(8.dp)
                                                .background(Color.LightGray)
                                        )
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false }
                                        ) {
                                            usuariosDisponibles.forEach { usuario ->
                                                DropdownMenuItem(
                                                    text = { Text("${usuario.nombres} ${usuario.apellidos}") },
                                                    onClick = {
                                                        idUsuarioSeleccionado = usuario.idUsuario
                                                        expanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }

                                }
                            },
                            confirmButton = {
                                Button(onClick = {
                                    // Actualizamos `Solicitante` con los nuevos datos del formulario
                                    val solicitanteModificado = solicitanteAEditar?.solicitante?.copy(
                                        nombre = nombre,
                                        apellido = apellido,
                                        correo = correo,
                                        telefono = telefono
                                    )
                                    // Actualizamos `Usuario` en caso de que el usuario seleccionado haya cambiado
                                    val usuarioModificado = solicitanteAEditar?.usuario?.copy(
                                        idUsuario = idUsuarioSeleccionado ?: 0 // Utilizamos el idUsuario seleccionado
                                    )

                                    if (solicitanteModificado != null && usuarioModificado != null) {
                                        scope.launch {
                                            // Intentamos actualizar tanto el solicitante como el usuario
                                            solicitanteRepository.actualizar(solicitanteModificado)
                                            usuarioRepository.actualizar(usuarioModificado) // Solo si necesitas actualizar también el usuario

                                            // Recargar la lista completa para reflejar los cambios en la interfaz
                                            solicitantesConUsuarios = solicitanteRepository.obtenerSolicitantesConUsuarios()
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