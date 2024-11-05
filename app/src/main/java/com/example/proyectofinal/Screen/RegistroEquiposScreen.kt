package com.example.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

    val estados = listOf("Disponible", "Ocupado")

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
                                text = "Registro de Equipos de Cómputo",
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

                    // Contenido en primer plano dentro de un LazyColumn
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
                                // Imagen encima de la Card
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

                                // Card debajo de la imagen
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(500.dp)
                                        .padding(top = 100.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.8f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Registrar Computador",
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Campo Marca
                                        TextField(
                                            value = marca,
                                            onValueChange = { marca = it },
                                            label = { Text("Marca") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Color(0xFFFAFAFA),
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                                unfocusedLabelColor = Color.Gray
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Campo Modelo
                                        TextField(
                                            value = modelo,
                                            onValueChange = { modelo = it },
                                            label = { Text("Modelo") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Color(0xFFFAFAFA),
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                                unfocusedLabelColor = Color.Gray
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Campo Número de Serie
                                        TextField(
                                            value = numeroSerie,
                                            onValueChange = { numeroSerie = it },
                                            label = { Text("Número de Serie") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Color(0xFFFAFAFA),
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                                unfocusedLabelColor = Color.Gray
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Estado Dropdown
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
                                                if (marca.isNotBlank() && modelo.isNotBlank() && numeroSerie.isNotBlank() && estado != "Seleccionar Estado") {
                                                    onSaveEquipo(marca.trim(), modelo.trim(), numeroSerie.trim(), estado) // Llama a la función con los valores ingresados y limpiados

                                                    // Limpia los campos después de guardar
                                                    marca = ""
                                                    modelo = ""
                                                    numeroSerie = ""
                                                    estado = "Seleccionar Estado"
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(56.dp)
                                                .background(
                                                    MaterialTheme.colorScheme.primary,
                                                    RoundedCornerShape(12.dp)
                                                ),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary,
                                                contentColor = Color.White
                                            )
                                        ) {
                                            Text(
                                                text = "Registrar Computador",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
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
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        // Botón que despliega el menú
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedEstado,
                color = if (selectedEstado == "Seleccionar Estado") Color.Gray else Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Menú desplegable
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            ) {
                estados.forEach { estadoOpcion ->
                    TextButton(
                        onClick = {
                            onEstadoSelected(estadoOpcion) // Actualiza el estado al seleccionar una opción
                            expanded = false // Cierra el dropdown después de seleccionar
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = estadoOpcion)
                    }
                }
            }
        }
    }
}
