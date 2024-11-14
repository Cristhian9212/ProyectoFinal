package com.example.proyectofinal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrosequiposScreen(
    navController: NavController, onSaveEquipo: (String, String, String, String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("Seleccionar Estado") }
    var errorMessage by remember { mutableStateOf("") }

    val estados = listOf("Disponible", "Ocupado")

    val onNavigate: (String) -> Unit = { route ->
        navController.navigate(route)
        scope.launch { drawerState.close() }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    BackHandler {
        // Navega hacia la pantalla inicial cuando el usuario retrocede
        navController.navigate("interfaz_inicial") {
            popUpTo("interfaz_inicial") { inclusive = true }
        }
    }
    // Mostrar el mensaje en la notificación temporal
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
                                text = "Registrar equipo",
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
                            .padding(24.dp) // Aumenta el padding para hacerlo más grande
                            .fillMaxWidth(0.9f) // Opcional, para que ocupe casi todo el ancho
                            .height(70.dp), // Ajusta la altura para hacer el Snackbar más grande
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Imagen personalizada
                                Image(
                                    painter = painterResource(id = R.drawable.udec), // Reemplaza "tu_imagen" con el nombre de tu imagen
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp) // Aumenta el tamaño de la imagen
                                )
                                Spacer(modifier = Modifier.width(12.dp)) // Mayor espacio entre imagen y texto

                                // Texto del Snackbar
                                Text(
                                    text = data.visuals.message,
                                    fontSize = 18.sp, // Aumenta el tamaño del texto
                                    fontWeight = FontWeight.Bold // Para hacer el texto más visible
                                )
                            }
                        }
                    )
                }
            }
            ,
            content = { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val backgroundImage: Painter = painterResource(id = R.drawable.fondosolo)
                    Image(
                        painter = backgroundImage,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = 0.8f))
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.estudiante),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .align(Alignment.TopCenter)
                                        .offset(y = (-90).dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(550.dp)
                                        .padding(top = 150.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        TextField(
                                            value = marca,
                                            onValueChange = { marca = it },
                                            label = { Text("Marca") },
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
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        TextField(
                                            value = modelo,
                                            onValueChange = { modelo = it },
                                            label = { Text("Modelo") },
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
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        TextField(
                                            value = numeroSerie,
                                            onValueChange = { numeroSerie = it },
                                            label = { Text("Número de Serie") },
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
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        EstadoDropdown(
                                            estados = estados,
                                            selectedEstado = estado,
                                            onEstadoSelected = { selectedEstado ->
                                                estado = selectedEstado
                                            }
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Button(
                                            onClick = {
                                                // Verifica que todos los campos estén llenos
                                                if (marca.isEmpty() || modelo.isEmpty() || numeroSerie.isEmpty() || estado == "Seleccionar Estado") {
                                                    errorMessage = "Todos los campos son obligatorios." // Actualiza el mensaje
                                                } else {
                                                    onSaveEquipo(marca.trim(), modelo.trim(), numeroSerie.trim(), estado)
                                                    marca = ""
                                                    modelo = ""
                                                    numeroSerie = ""
                                                    estado = "Seleccionar Estado"
                                                    errorMessage = "Equipo registrado con éxito." // Actualiza el mensaje
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp)
                                                .background(
                                                    Brush.linearGradient(
                                                        colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784)),
                                                        start = Offset(0f, 0f),
                                                        end = Offset(1f, 1f)
                                                    ),
                                                    shape = RoundedCornerShape(8.dp)
                                                ),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                                        ) {
                                            Text("Guardar", fontSize = 20.sp, color = Color.White)
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
fun EstadoDropdown(
    estados: List<String>,
    selectedEstado: String,
    onEstadoSelected: (String) -> Unit
) {
    var estadoExpanded by remember { mutableStateOf(false) }

    // Box principal para el dropdown
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 4.dp)
            .background(Color.Transparent)
            .border(BorderStroke(1.dp, Color(0xFF4CAF50)), shape = MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .clickable { estadoExpanded = true }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Texto dentro del cuadro
        Text(
            text = selectedEstado,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        // DropdownMenu que aparece al hacer clic
        DropdownMenu(
            expanded = estadoExpanded,
            onDismissRequest = { estadoExpanded = false },
            modifier = Modifier
                .fillMaxWidth() // Asegura que el DropdownMenu tenga el mismo ancho que el Box
                .background(Color.White)
                .offset(y = 4.dp) // Desplaza el menú justo debajo del Box sin espacios grandes
        ) {
            // Iterar sobre la lista de estados
            estados.forEach { estado ->
                DropdownMenuItem(
                    text = { Text(estado) },
                    onClick = {
                        onEstadoSelected(estado)
                        estadoExpanded = false
                    }
                )
            }
        }
    }
}
