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
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Repository.SolicitanteRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarSolicitantes(navController: NavController, solicitanteRepository: SolicitanteRepository) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var solicitantes by remember { mutableStateOf(listOf<Solicitante>()) }
    var solicitanteAEditar by remember { mutableStateOf<Solicitante?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        solicitantes = solicitanteRepository.obtenerTodosSolicitantes()
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
                        items(solicitantes) { solicitante ->
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

                                    // Row for edit/delete icons
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
                                                    // Set the fields to the current solicitante's info
                                                    solicitanteAEditar = solicitante
                                                    nombre = solicitante.nombre
                                                    apellido = solicitante.apellido
                                                    correo = solicitante.correo
                                                    telefono = solicitante.telefono
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
                                                        solicitantes = solicitanteRepository.obtenerTodosSolicitantes()
                                                    }
                                                },
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Edit dialog
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
                                }
                            },
                            confirmButton = {
                                Button(onClick = {
                                    val solicitanteModificado = solicitanteAEditar?.copy(
                                        nombre = nombre,
                                        apellido = apellido,
                                        correo = correo,
                                        telefono = telefono
                                    )
                                    solicitanteModificado?.let {
                                        scope.launch {
                                            solicitanteRepository.actualizar(it)
                                            solicitantes = solicitanteRepository.obtenerTodosSolicitantes()
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
