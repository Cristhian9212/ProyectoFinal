package com.example.proyectofinal

import android.database.sqlite.SQLiteConstraintException
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Search
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
import com.example.proyectofinal.Model.Computador
import com.example.proyectofinal.Repository.ComputadorRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarComputadores(navController: NavController, computadorRepository: ComputadorRepository) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var computadores by remember { mutableStateOf(listOf<Computador>()) }
    var computadorAEditar by remember { mutableStateOf<Computador?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    // Estado para el filtro de búsqueda
    var query by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        computadores = computadorRepository.obtenerTodosLosComputadores()
    }

    BackHandler {
        // Navega hacia la pantalla inicial cuando el usuario retrocede
        navController.navigate("interfaz-listar") {
            popUpTo("interfaz-listar") { inclusive = true }
        }
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
                                    painter = painterResource(id = R.drawable.udec), // Imagen personalizada
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
                                text = "Computadores registrados",
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val backgroundImage = painterResource(id = R.drawable.fondoprincipal)
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
                            .padding(paddingValues)
                    ) {
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it },
                            label = { Text("Buscar") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar",
                                    tint = Color.Black
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), // Relleno para que no quede pegado
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black, // Borde negro cuando está enfocado
                                unfocusedBorderColor = Color.Black // Borde negro cuando no está enfocado
                            )
                        )

                        // Contenido en primer plano
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(computadores.filter {
                                it.marca.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.modelo.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.numeroSerie.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.estado.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }) { computador ->
                                var iconVisible by remember { mutableStateOf(false) }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    elevation = CardDefaults.cardElevation(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(alpha = 0.5f)// Color blanco para la tarjeta
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Información del computador
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Marca: ${computador.marca}",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Modelo: ${computador.modelo}",
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "Número de Serie: ${computador.numeroSerie}",
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                text = "Estado: ${computador.estado}",
                                                fontSize = 14.sp
                                            )
                                        }

                                        // Íconos de editar y eliminar
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        computadorAEditar = computador
                                                        marca = computador.marca
                                                        modelo = computador.modelo
                                                        numeroSerie = computador.numeroSerie
                                                        estado = computador.estado
                                                        mostrarDialogoEditar = true
                                                    },
                                                tint = Color.Black
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Eliminar",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        scope.launch {
                                                            try{
                                                                computadorRepository.eliminar(computador)
                                                                computadores = computadorRepository.obtenerTodosLosComputadores()
                                                            }catch (e: SQLiteConstraintException){
                                                                snackbarHostState.showSnackbar(
                                                                    "No se puede eliminar: el computador está asignado a otros registros."
                                                                )
                                                            }
                                                        }
                                                    },
                                                tint = Color(color = 0xFFE20000)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Dialogo para editar el computador
                        if (mostrarDialogoEditar && computadorAEditar != null) {
                            AlertDialog(
                                onDismissRequest = { mostrarDialogoEditar = false },
                                title = { Text("Editar Computador") },
                                text = {
                                    Column {
                                        Text("Modifica los campos del computador aquí")
                                        TextField(
                                            value = marca,
                                            onValueChange = { marca = it },
                                            label = { Text("Marca") }
                                        )
                                        TextField(
                                            value = modelo,
                                            onValueChange = { modelo = it },
                                            label = { Text("Modelo") }
                                        )
                                        TextField(
                                            value = numeroSerie,
                                            onValueChange = { numeroSerie = it },
                                            label = { Text("Número de Serie") }
                                        )
                                        TextField(
                                            value = estado,
                                            onValueChange = { estado = it },
                                            label = { Text("Estado") }
                                        )
                                    }
                                },
                                confirmButton = {
                                    IconButton(onClick = {
                                        // Lógica para guardar cambios en el computador
                                        val computadorModificado = computadorAEditar?.copy(
                                            marca = marca,
                                            modelo = modelo,
                                            numeroSerie = numeroSerie,
                                            estado = estado
                                        )
                                        computadorModificado?.let {
                                            scope.launch {
                                                computadorRepository.actualizar(it)
                                                computadores = computadorRepository.obtenerTodosLosComputadores()
                                            }
                                        }
                                        mostrarDialogoEditar = false
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Done, // Icono de "Guardar"
                                            contentDescription = "Guardar"
                                        )
                                    }
                                },
                                dismissButton = {
                                    IconButton(onClick = { mostrarDialogoEditar = false }) {
                                        Icon(
                                            imageVector = Icons.Default.Close, // Icono de "Cancelar"
                                            contentDescription = "Cancelar"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
                SnackbarHost(hostState = snackbarHostState)
            }
        )
    }
}