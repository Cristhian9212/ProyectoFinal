package com.example.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterfazInicialScreen(onNavigate: () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            // Renderiza el contenido del drawer solo si está abierto
            if (drawerState.isOpen) {
                DrawerContent(onNavigate)
            }
        },
        drawerState = drawerState,
        gesturesEnabled = false // Desactiva el gesto de arrastre para abrir el drawer
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100 .dp) // Aumenta la altura de la barra
                ) {
                    // Usar un fondo personalizado que se extienda de lado a lado
                    Image(
                        painter = painterResource(id = R.drawable.estudiante), // Reemplaza con el nombre de tu imagen
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize() // Asegúrate de que la imagen llene el tamaño del Box
                            .align(Alignment.Center), // Centrar la imagen si es necesario
                        contentScale = ContentScale.Crop // Cambiar a Crop para que la imagen ocupe todo el espacio
                    )

                    TopAppBar(
                        title = {
                            Text(
                                text = "Inicio",
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
                            containerColor = Color.Transparent // Hacer transparente el color de fondo
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp) // Añadir padding horizontal
                    )
                }
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White) // Fondo blanco desde la barra hacia abajo
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Bienvenido a la Interfaz Inicial",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }
}

@Composable
fun DrawerContent(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Color de fondo del drawer
    ) {
        Text(
            text = "Menú Principal",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2AA345),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextButton(
            onClick = { /* Navegar a Sección 1 */ },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2AA345))
        ) {
            Text(text = "Sección 1")
        }
        TextButton(
            onClick = { /* Navegar a Sección 2 */ },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2AA345))
        ) {
            Text(text = "Sección 2")
        }
        TextButton(
            onClick = { /* Navegar a Sección 3 */ },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2AA345))
        ) {
            Text(text = "Sección 3")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNavigate() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2AA345),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(12.dp) // Bordes redondeados para el botón
        ) {
            Text(text = "Cerrar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}
