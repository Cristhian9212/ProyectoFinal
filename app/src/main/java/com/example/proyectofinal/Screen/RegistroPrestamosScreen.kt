package com.example.proyectofinal

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
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
import com.example.proyectofinal.Repository.PrestamoRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroPrestamosScreen
            (navController: NavController,
             prestamoRepository: PrestamoRepository,
             onSaveEquipo: (Int, Int, String, String, String) -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var idSolicitante by remember { mutableStateOf("") }
    var idComputador by remember { mutableStateOf("") }
    var fechaPrestamo by remember { mutableStateOf("") }
    var fechaDevolucion by remember { mutableStateOf("") }
    var fechaDevuelta by remember { mutableStateOf("") }

    // Estados para mostrar los DatePickerDialog
    var isDatePickerVisiblePrestamo by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevolucion by remember { mutableStateOf(false) }
    var isDatePickerVisibleDevuelta by remember { mutableStateOf(false) }

    BackHandler(enabled = drawerState.isClosed) {
        // Acción vacía para deshabilitar el botón de retroceso
    }

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
                                text = "Registro de Préstamos",
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
                    // Imagen de fondo
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

                    // Contenido en primer plano
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Campo para el ID del solicitante
                        TextField(
                            value = idSolicitante,
                            onValueChange = { idSolicitante = it },
                            label = { Text("ID del Solicitante", color = Color.Black.copy(alpha = 0.8f)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Campo para el ID del computador
                        TextField(
                            value = idComputador,
                            onValueChange = { idComputador = it },
                            label = { Text("ID del Computador", color = Color.Black.copy(alpha = 0.8f)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Campo para la fecha de préstamo
                        TextField(
                            value = fechaPrestamo,
                            onValueChange = { fechaPrestamo = it },
                            label = { Text("Fecha de Préstamo", color = Color.Black.copy(alpha = 0.8f)) },
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
                                    fechaPrestamo = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
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
                            label = { Text("Fecha de Devolución", color = Color.Black.copy(alpha = 0.8f)) },
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
                                    fechaDevolucion = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                    isDatePickerVisibleDevolucion = false
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }

                        // Campo para la fecha devuelta
                        TextField(
                            value = fechaDevuelta,
                            onValueChange = { fechaDevuelta = it },
                            label = { Text("Fecha Real de Devolución", color = Color.Black.copy(alpha = 0.8f)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            trailingIcon = {
                                IconButton(onClick = { isDatePickerVisibleDevuelta = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Seleccionar fecha devuelta",
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
                                    fechaDevuelta = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                                    isDatePickerVisibleDevuelta = false
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                    }
                }
            }
        )
    }
}
