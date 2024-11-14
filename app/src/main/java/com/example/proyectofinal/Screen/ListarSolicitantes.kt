package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                                text = "Estudiantes inscritos",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(6.dp) // Añade relleno blanco alrededor del texto
                                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp)) // Borde negro
                                    .padding(6.dp) // Añade relleno blanco dentro del borde
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
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
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
                                    }

                                    // Íconos de Editar y Eliminar a la derecha
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .align(Alignment.CenterVertically),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                                    idUsuarioSeleccionado = usuario.idUsuario
                                                    mostrarDialogoEditar = true
                                                },
                                            tint = Color.Blue
                                        )
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

                                    // Descripción para el campo de usuario ingresado por
                                    Text(
                                        text = "Ingresado por:",
                                        modifier = Modifier.padding(top = 8.dp)
                                    )

                                    // Dropdown menu estilizado
                                    var expanded by remember { mutableStateOf(false) }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp)
                                            .background(Color.Transparent)
                                            .border(
                                                BorderStroke(1.dp, Color.Gray),
                                                shape = MaterialTheme.shapes.small
                                            )
                                            .clip(MaterialTheme.shapes.small)
                                            .clickable { expanded = true }
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = usuariosDisponibles.find { it.idUsuario == idUsuarioSeleccionado }
                                                ?.let { "${it.nombres} ${it.apellidos}" }
                                                ?: "Seleccionar Usuario",
                                            color = Color.Black
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
                                IconButton(onClick = {
                                    val solicitanteModificado = solicitanteAEditar?.solicitante?.copy(
                                        nombre = nombre,
                                        apellido = apellido,
                                        correo = correo,
                                        telefono = telefono,
                                        idUsuario = idUsuarioSeleccionado ?: 0
                                    )

                                    if (solicitanteModificado != null) {
                                        scope.launch {
                                            solicitanteRepository.actualizar(solicitanteModificado)
                                            solicitantesConUsuarios = solicitanteRepository.obtenerSolicitantesConUsuarios()
                                        }
                                    }
                                    mostrarDialogoEditar = false
                                }) {
                                    Icon(imageVector = Icons.Default.Done, contentDescription = "Guardar")
                                }
                            },
                            dismissButton = {
                                IconButton(onClick = { mostrarDialogoEditar = false }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cancelar")
                                }
                            }
                        )
                    }


                }
            }
        )
    }
}