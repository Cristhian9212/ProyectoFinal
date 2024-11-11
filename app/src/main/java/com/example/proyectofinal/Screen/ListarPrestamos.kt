package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Model.PrestamoConDetalles // Asegúrate de importar esta clase correctamente
import com.example.proyectofinal.Repository.DetallesRepository
import com.example.proyectofinal.Repository.PrestamoRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarPrestamos(navController: NavController, detallesRepository: DetallesRepository) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Variable de estado para almacenar la lista de préstamos detallados
    var prestamos by remember { mutableStateOf(listOf<PrestamoConDetalles>()) }

    // Cargar los préstamos detallados de la base de datos al iniciar el Composable
    LaunchedEffect(Unit) {
        prestamos = detallesRepository.obtenerPrestamosConDetalles()
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
                                text = "Computadores",
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
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Lista de Préstamos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Usar LazyColumn para listar las Cards independientes
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
                                            text = "Correo: ${prestamoConDetalles.solicitante.correo}",
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
