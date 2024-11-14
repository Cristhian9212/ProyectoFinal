package com.example.proyectofinal

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.proyectofinal.Repository.ComputadorRepository
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
    var errorMessage by remember { mutableStateOf("") }
    val solicitantes = remember { mutableStateOf<List<Solicitante>>(emptyList()) }
    val computadores = remember { mutableStateOf<List<Computador>>(emptyList()) }
    val snackbarHostState = remember { SnackbarHostState() }

    var isDatePickerVisiblePrestamo by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevolucion by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevuelta by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        solicitantes.value = solicitanteRepository.obtenerTodosSolicitantes()
        computadores.value = computadorRepository.obtenerTodosLosComputadores()
    }
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(errorMessage)
        }
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
                                text = "Registro para préstamos",
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
                                Image(
                                    painter = painterResource(id = R.drawable.udec),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))

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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    // Imagen principal
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.registrar),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(top = 30.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                    }

                    // Card principal
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValues) // Añade el padding recibido aquí
                                .offset(y = -50.dp)
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
                                        .height(60.dp) // Ajusta la altura según tus necesidades
                                        .padding(top = 4.dp)
                                        .background(Color.Transparent)
                                        .border(BorderStroke(1.dp, Color(0xFF4CAF50)), shape = MaterialTheme.shapes.small)
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { expandedSolicitante = true } // Manejo del estado de expansión
                                ) {
                                    Text(
                                        text = selectedSolicitante?.let { "${it.nombre} ${it.apellido}" } ?: "Seleccionar Usuario",
                                        color = Color.Black,
                                        modifier = Modifier.padding(vertical = 18.dp, horizontal = 15.dp) // Ajusta el padding dentro del Box
                                    )
                                }

                                DropdownMenu(
                                    expanded = expandedSolicitante,
                                    onDismissRequest = { expandedSolicitante = false },
                                    modifier = Modifier
                                        .fillMaxWidth() // Asegura que el DropdownMenu tenga el mismo ancho que el Box
                                        .background(Color.White)
                                        .offset(y = 4.dp) // Desplaza el menú justo debajo del Box sin espacios grandes
                                ) {
                                    solicitantes.value.forEach { solicitante ->
                                        DropdownMenuItem(
                                            text = { Text("${solicitante.nombre} ${solicitante.apellido}") },
                                            onClick = {
                                                selectedSolicitante = solicitante
                                                idSolicitante = solicitante.idSolicitante
                                                expandedSolicitante = false // Cerrar el menú después de seleccionar
                                            }
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp) // Ajusta la altura según tus necesidades
                                        .padding(top = 4.dp)
                                        .background(Color.Transparent)
                                        .border(BorderStroke(1.dp, Color(0xFF4CAF50)), shape = MaterialTheme.shapes.small)
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { expandedComputador = true } // Manejo del estado de expansión
                                ) {
                                    Text(
                                        text = selectedComputador?.let { "${it.marca} ${it.modelo}" } ?: "Seleccionar Computador",
                                        color = Color.Black,
                                        modifier = Modifier.padding(vertical = 18.dp, horizontal = 15.dp) // Ajusta el padding dentro del Box
                                    )
                                }

                                DropdownMenu(
                                    expanded = expandedComputador,
                                    onDismissRequest = { expandedComputador = false },
                                    modifier = Modifier
                                        .fillMaxWidth() // Asegura que el DropdownMenu tenga el mismo ancho que el Box
                                        .background(Color.White)
                                        .offset(y = 4.dp) // Desplaza el menú justo debajo del Box sin espacios grandes
                                ) {
                                    computadores.value.forEach { computador ->
                                        DropdownMenuItem(
                                            text = { Text("${computador.marca} ${computador.modelo}") },
                                            onClick = {
                                                selectedComputador = computador
                                                idComputador = computador.idComputador
                                                expandedComputador = false // Cerrar el menú después de seleccionar
                                            }
                                        )
                                    }
                                }

                                TextField(
                                    value = fechaPrestamo,
                                    onValueChange = { fechaPrestamo = it },
                                    label = { Text("Fecha de Préstamo", color = Color.Black.copy(alpha = 0.8f)) },
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedLabelColor = Color(0xFF4CAF50),
                                        unfocusedLabelColor = Color.Gray,
                                        containerColor = Color.Transparent,
                                        cursorColor = Color(0xFF4CAF50)
                                    ),
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp)) // Borde verde
                                )

                                if (isDatePickerVisiblePrestamo) {
                                    val calendar = Calendar.getInstance()
                                    DatePickerDialog(
                                        LocalContext.current,
                                        { _, year, month, dayOfMonth ->
                                            fechaPrestamo = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                            isDatePickerVisiblePrestamo = false
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }

                                TextField(
                                    value = fechaDevolucion,
                                    onValueChange = { fechaDevolucion = it },
                                    label = { Text("Fecha de Devolución", color = Color.Black.copy(alpha = 0.8f)) },
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
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    trailingIcon = {
                                        IconButton(onClick = { isDatePickerVisibleDevolucion = true }) {
                                            Icon(
                                                imageVector = Icons.Filled.DateRange,
                                                contentDescription = "Seleccionar fecha de devolución",
                                                tint = Color.Gray
                                            )
                                        }
                                    }
                                )

                                if (isDatePickerVisibleDevolucion) {
                                    val calendar = Calendar.getInstance()
                                    DatePickerDialog(
                                        LocalContext.current,
                                        { _, year, month, dayOfMonth ->
                                            fechaDevolucion = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                            isDatePickerVisibleDevolucion = false
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }

                                TextField(
                                    value = fechaDevuelta,
                                    onValueChange = { fechaDevuelta = it },
                                    label = { Text("Fecha de Devolución Real", color = Color.Black.copy(alpha = 0.8f)) },
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedLabelColor = Color(0xFF4CAF50),
                                        unfocusedLabelColor = Color.Gray,
                                        containerColor = Color.Transparent,
                                        cursorColor = Color(0xFF4CAF50)
                                    ),
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(8.dp)) // Borde verde
                                )

                                if (isDatePickerVisibleDevuelta) {
                                    val calendar = Calendar.getInstance()
                                    DatePickerDialog(
                                        LocalContext.current,
                                        { _, year, month, dayOfMonth ->
                                            fechaDevuelta = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
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
                                        val solicitanteId = idSolicitante // Asignación a una variable local
                                        val computadorId = idComputador // Asignación a una variable local

                                        // Verificar si los campos requeridos están completos
                                        if (solicitanteId == null || computadorId == null || fechaPrestamo.isEmpty() || fechaDevolucion.isEmpty()) {
                                            errorMessage = "Todos los campos son obligatorios."
                                        } else if (fechaDevuelta.isNotEmpty() && fechaDevuelta < fechaDevolucion) {
                                            // Mensaje de error si la fecha devuelta es anterior a la de devolución
                                            errorMessage = "La fecha devuelta no puede ser anterior a la fecha de devolución."
                                        } else {
                                            // Guardar sólo si no hay error en los campos
                                            onSaveEquipo(
                                                solicitanteId,
                                                computadorId,
                                                fechaPrestamo,
                                                fechaDevolucion,
                                                fechaDevuelta
                                            )

                                            // Limpiar los campos después de guardar
                                            fechaPrestamo = ""
                                            fechaDevolucion = ""
                                            fechaDevuelta = ""
                                            idSolicitante = null
                                            idComputador = null
                                            selectedSolicitante = null
                                            selectedComputador = null

                                            // Mensaje de éxito
                                            errorMessage = "Préstamo registrado con éxito."
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF4CAF50) // Siempre verde
                                    )
                                ) {
                                    Text("Registrar Préstamo", fontSize = 20.sp, color = Color.White)
                                }

                            }
                        }
                    }
                }
            }
        )
    }
}
