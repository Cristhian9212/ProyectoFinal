package com.example.proyectofinal

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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Delete
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
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.UsuarioRepository
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listarUsuarios(
    navController: NavController,
    usuarioRepository: UsuarioRepository
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var usuarios by remember { mutableStateOf(listOf<Usuario>()) }
    var usuarioAEditar by remember { mutableStateOf<Usuario?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // Estado para el filtro de búsqueda
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        usuarios = usuarioRepository.obtenerTodosUsuarios()
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
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp) // Ajustamos la altura para incluir la imagen iniciosup
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.iniciosup),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        contentScale = ContentScale.Crop
                    )
                    TopAppBar(
                        title = {
                            Text(
                                text = "Listado de usuarios",
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
                        // Barra de búsqueda fuera de iniciosup
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
                        // Listado de usuarios
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(usuarios.filter {
                                it.nombres.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.apellidos.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.cargo.contains(
                                    query,
                                    ignoreCase = true
                                ) || it.correo.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }) { usuario ->
                                var iconVisible by remember { mutableStateOf(false) }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    elevation = CardDefaults.cardElevation(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(alpha = 0.5f) // Color blanco para la tarjeta
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Información del usuario
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Nombre: ${usuario.nombres} ${usuario.apellidos}",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Cargo: ${usuario.cargo}",
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "Correo: ${usuario.correo}",
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
                                                        usuarioAEditar = usuario
                                                        nombres = usuario.nombres
                                                        apellidos = usuario.apellidos
                                                        cargo = usuario.cargo
                                                        correo = usuario.correo
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
                                                            usuarioRepository.eliminar(usuario)
                                                            usuarios =
                                                                usuarioRepository.obtenerTodosUsuarios()
                                                        }
                                                    },
                                                tint = Color(0xFFE20000)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        // Dialogo para editar el usuario
                        if (mostrarDialogoEditar && usuarioAEditar != null) {
                            AlertDialog(
                                onDismissRequest = { mostrarDialogoEditar = false },
                                title = { Text("Editar Usuario") },
                                text = {
                                    Column {
                                        Text("Modifica los campos del usuario aquí")
                                        TextField(
                                            value = nombres,
                                            onValueChange = { nombres = it },
                                            label = { Text("Nombres") }
                                        )
                                        TextField(
                                            value = apellidos,
                                            onValueChange = { apellidos = it },
                                            label = { Text("Apellidos") }
                                        )
                                        TextField(
                                            value = cargo,
                                            onValueChange = { cargo = it },
                                            label = { Text("Cargo") }
                                        )
                                        TextField(
                                            value = correo,
                                            onValueChange = { correo = it },
                                            label = { Text("Correo") }
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        // Lógica para guardar cambios en el usuario
                                        val usuarioModificado = usuarioAEditar?.copy(
                                            nombres = nombres,
                                            apellidos = apellidos,
                                            cargo = cargo,
                                            correo = correo
                                        )
                                        usuarioModificado?.let {
                                            scope.launch {
                                                usuarioRepository.actualizar(it)
                                                usuarios = usuarioRepository.obtenerTodosUsuarios()
                                                mostrarDialogoEditar = false
                                            }
                                        }
                                    }) {
                                        Text("Guardar Cambios")
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
            }
        )
    }
}
