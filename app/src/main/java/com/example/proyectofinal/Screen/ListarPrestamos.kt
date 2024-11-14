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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Model.Computador
import com.example.proyectofinal.Model.Prestamo
import com.example.proyectofinal.Model.PrestamoConDetalles
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Repository.DetallesRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarPrestamos(
    navController: NavController,
    detallesRepository: DetallesRepository,
    solicitantes: List<Solicitante>,
    computadores: List<Computador>
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var prestamos by remember { mutableStateOf(listOf<PrestamoConDetalles>()) }
    var prestamoAEditar by remember { mutableStateOf<Prestamo?>(null) }

    LaunchedEffect(Unit) {
        prestamos = detallesRepository.obtenerPrestamosConDetalles()
    }
    BackHandler(enabled = drawerState.isClosed) {}

    if (prestamoAEditar != null) {
        EditarPrestamoDialog(
            prestamo = prestamoAEditar!!,
            solicitantes = solicitantes,
            computadores = computadores,
            onDismiss = { prestamoAEditar = null },
            onSave = { prestamoEditado ->
                scope.launch {
                    detallesRepository.actualizarPrestamo(prestamoEditado)
                    prestamos = detallesRepository.obtenerPrestamosConDetalles()
                    prestamoAEditar = null
                }
            }
        )
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
                    DrawerContent(onNavigate = { route ->
                        navController.navigate(route)
                        scope.launch { drawerState.close() }
                    })
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
                                text = "Computadores prestados",
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
                }
            },
            content = { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()) {
                    val backgroundImage: Painter = painterResource(id = R.drawable.fondoprincipal)
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Lista de Préstamos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(prestamos) { prestamoConDetalles ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Solicitante: ${prestamoConDetalles.solicitante.nombre} ${prestamoConDetalles.solicitante.apellido}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Computador: ${prestamoConDetalles.computador.marca} ${prestamoConDetalles.computador.modelo}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Número de Serie: ${prestamoConDetalles.computador.numeroSerie}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Fecha Préstamo: ${prestamoConDetalles.prestamo.fechaPrestamo}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Fecha Devolución: ${prestamoConDetalles.prestamo.fechaDevolucion}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Fecha Devuelta: ${prestamoConDetalles.prestamo.fechaDevuelta ?: "No devuelto"}",
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Button(
                                                onClick = { prestamoAEditar = prestamoConDetalles.prestamo },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Blue
                                                )
                                            ) {
                                                Text("Modificar", color = Color.White)
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Button(
                                                onClick = {
                                                    scope.launch {
                                                        detallesRepository.eliminarPrestamo(prestamoConDetalles.prestamo)
                                                        prestamos = detallesRepository.obtenerPrestamosConDetalles()
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Red
                                                )
                                            ) {
                                                Text("Eliminar", color = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun EditarPrestamoDialog(
    prestamo: Prestamo,
    solicitantes: List<Solicitante>,
    computadores: List<Computador>,
    onDismiss: () -> Unit,
    onSave: (Prestamo) -> Unit
) {
    var fechaPrestamo by remember { mutableStateOf(prestamo.fechaPrestamo) }
    var fechaDevolucion by remember { mutableStateOf(prestamo.fechaDevolucion) }
    var fechaDevuelta by remember { mutableStateOf(prestamo.fechaDevuelta ?: "") }
    var idSolicitante by remember { mutableStateOf(prestamo.idSolicitante) }
    var idComputador by remember { mutableStateOf(prestamo.idComputador) }

    var solicitanteExpanded by remember { mutableStateOf(false) }
    var computadorExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Préstamo") },
        text = {
            Column {
                // Selector de idSolicitante
                Text("Solicitante")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .background(Color.Transparent)
                        .border(BorderStroke(1.dp, Color.Gray), shape = MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { solicitanteExpanded = true }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = solicitantes.find { it.idSolicitante == idSolicitante }
                            ?.let { "${it.nombre} ${it.apellido}" } ?: "Seleccionar Solicitante",
                        color = Color.Black
                    )

                    DropdownMenu(
                        expanded = solicitanteExpanded,
                        onDismissRequest = { solicitanteExpanded = false }
                    ) {
                        solicitantes.forEach { solicitante ->
                            DropdownMenuItem(
                                text = { Text("${solicitante.nombre} ${solicitante.apellido}") },
                                onClick = {
                                    idSolicitante = solicitante.idSolicitante
                                    solicitanteExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Selector de idComputador
                Text("Computador")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .background(Color.Transparent)
                        .border(BorderStroke(1.dp, Color.Gray), shape = MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { computadorExpanded = true }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = computadores.find { it.idComputador == idComputador }
                            ?.let { "${it.marca} ${it.modelo}" } ?: "Seleccionar Computador",
                        color = Color.Black
                    )

                    DropdownMenu(
                        expanded = computadorExpanded,
                        onDismissRequest = { computadorExpanded = false }
                    ) {
                        computadores.forEach { computador ->
                            DropdownMenuItem(
                                text = { Text("${computador.marca} ${computador.modelo}") },
                                onClick = {
                                    idComputador = computador.idComputador
                                    computadorExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de texto para fechas
                OutlinedTextField(
                    value = fechaPrestamo,
                    onValueChange = { fechaPrestamo = it },
                    label = { Text("Fecha Préstamo") }
                )
                OutlinedTextField(
                    value = fechaDevolucion,
                    onValueChange = { fechaDevolucion = it },
                    label = { Text("Fecha Devolución") }
                )
                OutlinedTextField(
                    value = fechaDevuelta,
                    onValueChange = { fechaDevuelta = it },
                    label = { Text("Fecha Devuelta") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(prestamo.copy(
                    idSolicitante = idSolicitante,
                    idComputador = idComputador,
                    fechaPrestamo = fechaPrestamo,
                    fechaDevolucion = fechaDevolucion,
                    fechaDevuelta = if (fechaDevuelta.isBlank()) null else fechaDevuelta
                ))
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
