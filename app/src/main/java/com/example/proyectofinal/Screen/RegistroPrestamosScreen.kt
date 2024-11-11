package com.example.proyectofinal

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Model.Computador
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.ComputadorRepository
import com.example.proyectofinal.Repository.PrestamoRepository
import com.example.proyectofinal.Repository.SolicitanteRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroPrestamosScreen(
    navController: NavController,
    solicitanteRepository: SolicitanteRepository,
    computadorRepository: ComputadorRepository,
    onSaveEquipo: (Int, Int, String, String, String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var fechaPrestamo by remember { mutableStateOf("") }
    var fechaDevolucion by remember { mutableStateOf("") }
    var fechaDevuelta by remember { mutableStateOf("") }

    var idSolicitante by remember { mutableStateOf<Int?>(null) }
    var expandedSolicitante by remember { mutableStateOf(false) }
    var selectedSolicitante by remember { mutableStateOf<Solicitante?>(null) }

    var idComputador by remember { mutableStateOf<Int?>(null) }
    var expandedComputador by remember { mutableStateOf(false) }
    var selectedComputador by remember { mutableStateOf<Computador?>(null) }

    val solicitantes = remember { mutableStateOf<List<Solicitante>>(emptyList()) }
    val computadores = remember { mutableStateOf<List<Computador>>(emptyList()) }

    // Estados para mostrar los DatePickerDialog
    var isDatePickerVisiblePrestamo by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevolucion by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevuelta by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        solicitantes.value = solicitanteRepository.obtenerTodosSolicitantes()
        computadores.value = computadorRepository.obtenerTodosLosComputadores()
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
                                text = "Registro para prestamos",
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Dropdown para seleccionar el solicitante
                    Text("Solicitante:")
                    OutlinedTextField(
                        value = selectedSolicitante?.let { "${it.nombre} ${it.apellido}" } ?: "",
                        onValueChange = {},
                        label = { Text("Seleccionar Solicitante") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedSolicitante = !expandedSolicitante }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Desplegar opciones"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedSolicitante,
                        onDismissRequest = { expandedSolicitante = false }
                    ) {
                        solicitantes.value.forEach { solicitante ->
                            DropdownMenuItem(
                                text = { Text("${solicitante.nombre} ${solicitante.apellido}") },
                                onClick = {
                                    selectedSolicitante = solicitante
                                    idSolicitante = solicitante.idSolicitante
                                    expandedSolicitante = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Dropdown para seleccionar el computador
                    Text("Computador:")
                    OutlinedTextField(
                        value = selectedComputador?.let { it.marca } ?: "",
                        onValueChange = {},
                        label = { Text("Seleccionar Computador") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedComputador = !expandedComputador }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Desplegar opciones"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedComputador,
                        onDismissRequest = { expandedComputador = false }
                    ) {
                        computadores.value.forEach { computador ->
                            DropdownMenuItem(
                                text = { Text(computador.marca) },
                                onClick = {
                                    selectedComputador = computador
                                    idComputador = computador.idComputador
                                    expandedComputador = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Campo para la fecha de préstamo
                    TextField(
                        value = fechaPrestamo,
                        onValueChange = { fechaPrestamo = it },
                        label = {
                            Text(
                                "Fecha de Préstamo",
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        trailingIcon = {
                            IconButton(onClick = { isDatePickerVisiblePrestamo = true }) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Seleccionar fecha de préstamo",
                                    tint = Color.Gray
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Muestra el DatePickerDialog si isDatePickerVisiblePrestamo es verdadero
                    if (isDatePickerVisiblePrestamo) {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            LocalContext.current,
                            { _, year, month, dayOfMonth ->
                                fechaPrestamo =
                                    String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                isDatePickerVisiblePrestamo = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    // Campo para la fecha de devolución
                    TextField(
                        value = fechaDevolucion,
                        onValueChange = { fechaDevolucion = it },
                        label = {
                            Text(
                                "Fecha de Devolución",
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        trailingIcon = {
                            IconButton(onClick = { isDatePickerVisibleDevolucion = true }) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Seleccionar fecha de devolución",
                                    tint = Color.Gray
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Muestra el DatePickerDialog si isDatePickerVisibleDevolucion es verdadero
                    if (isDatePickerVisibleDevolucion) {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            LocalContext.current,
                            { _, year, month, dayOfMonth ->
                                fechaDevolucion =
                                    String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                isDatePickerVisibleDevolucion = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    // Campo para la fecha de devolución real
                    TextField(
                        value = fechaDevuelta,
                        onValueChange = { fechaDevuelta = it },
                        label = {
                            Text(
                                "Fecha de Devolución Real",
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        trailingIcon = {
                            IconButton(onClick = { isDatePickerVisibleDevuelta = true }) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Seleccionar fecha de devolución real",
                                    tint = Color.Gray
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Muestra el DatePickerDialog si isDatePickerVisibleDevuelta es verdadero
                    if (isDatePickerVisibleDevuelta) {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            LocalContext.current,
                            { _, year, month, dayOfMonth ->
                                fechaDevuelta =
                                    String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                isDatePickerVisibleDevuelta = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (idSolicitante != null && idComputador != null) {
                                onSaveEquipo(
                                    idSolicitante!!,
                                    idComputador!!,
                                    fechaPrestamo,
                                    fechaDevolucion,
                                    fechaDevuelta
                                )

                                // Limpiar los campos después de guardar la información
                                fechaPrestamo = ""
                                fechaDevolucion = ""
                                fechaDevuelta = ""
                                idSolicitante = null
                                idComputador = null
                                selectedSolicitante = null
                                selectedComputador = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Registrar Préstamo")
                    }
                }
            }
        )
    }
}
