package com.example.proyectofinal.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinal.R

@Composable
fun DrawerContent(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.White, Color(0xFFE0E0E0)), // Degradado de blanco a gris claro
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            ))
    ){
        // Barra superior con imagen de fondo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp) // Altura de la barra superior
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondo), // Reemplaza 'tu_imagen' con el nombre de tu recurso de imagen
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Escala la imagen para que ocupe todo el espacio del Box
            )

            // Card en la barra superior
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x80FFFFFF)), // Fondo semitransparente (50% opaco)
                modifier = Modifier
                    .align(Alignment.Center) // Centra la Card en el Box
                    .padding(16.dp) // Espaciado alrededor de la Card
                    .size(300.dp, 150.dp) // Tamaño aumentado de la Card
            ) {
                Row(
                    modifier = Modifier.padding(16.dp), // Espaciado interno de la Card
                    verticalAlignment = Alignment.CenterVertically // Alinear verticalmente al centro
                ) {
                    // Ícono redondo
                    Surface(
                        shape = CircleShape,
                        color = Color(0x80FFFFFF), // Color de fondo del ícono
                        modifier = Modifier.size(40.dp) // Tamaño del ícono
                    ) {
                        // Reemplaza esto con tu ícono
                        Icon(
                            imageVector = Icons.Default.AccountBox, // Cambia esto por tu ícono
                            contentDescription = "Ícono de ejemplo",
                            modifier = Modifier.size(24.dp), // Tamaño del ícono dentro del círculo
                            tint = Color.Black // Color del ícono
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el ícono y el texto

                    Column {
                        Text(
                            text = "Título de la Card", // Cambia este texto según lo que necesites
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black // Cambia el color del texto si es necesario
                        )
                        Text(
                            text = "Descripción opcional", // Texto adicional
                            fontSize = 14.sp,
                            color = Color.Black // Cambia el color del texto si es necesario
                        )
                    }
                }
            }
        }

// Contenido del Drawer
        Spacer(modifier = Modifier.height(16.dp))


        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // Establece un ancho máximo para la Card
                .heightIn(max = 100.dp)
                .padding(8.dp), // Espaciado alrededor de la Card
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.equipos), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el espacio de la tarjeta
                        .align(Alignment.Center) // Centra la imagen
                        .clip(RoundedCornerShape(8.dp)) // Aplica bordes redondeados a la imagen
                        .graphicsLayer(alpha = 0.7f), // Establece la transparencia de la imagen
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espaciado interno de la Card
                        .align(Alignment.BottomStart) // Alinea el Row en la parte inferior
                    , verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    TextButton(
                        onClick = { onNavigate("registrosequipos") }, // Usa onNavigate aquí
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFFFFFF))
                    ) {
                        Text(text = "Registrar equipos")
                    }
                }
            }
        }


        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // Establece un ancho máximo para la Card
                .heightIn(max = 100.dp)
                .padding(8.dp), // Espaciado alrededor de la Card
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.estudiante), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el espacio de la tarjeta
                        .align(Alignment.Center) // Centra la imagen
                        .clip(RoundedCornerShape(8.dp)) // Aplica bordes redondeados a la imagen
                        .graphicsLayer(alpha = 0.7f), // Establece la transparencia de la imagen
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espaciado interno de la Card
                        .align(Alignment.BottomStart) // Alinea el Row en la parte inferior
                    , verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    TextButton(
                        onClick = { onNavigate("registrosolicitante")},
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = "Registrar solicitante")
                    }
                }
            }
        }

// Tarjeta para "Solicitud de préstamo"
        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // Establece un ancho máximo para la Card
                .heightIn(max = 100.dp)
                .padding(8.dp), // Espaciado alrededor de la Card
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            //  border = BorderStroke(2.dp, Color.Gray) // Añade un borde gris a la Card
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.estudiante), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el espacio de la tarjeta
                        .align(Alignment.Center) // Centra la imagen
                        .clip(RoundedCornerShape(8.dp)) // Aplica bordes redondeados a la imagen
                        .graphicsLayer(alpha = 0.7f), // Establece la transparencia de la imagen
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espaciado interno de la Card
                        .align(Alignment.BottomStart) // Alinea el Row en la parte inferior
                    , verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    TextButton(
                        onClick = {onNavigate("registro-prestamo")},
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = "Prestamos de equipos")
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // Establece un ancho máximo para la Card
                .heightIn(max = 100.dp)
                .padding(8.dp), // Espaciado alrededor de la Card
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            //  border = BorderStroke(2.dp, Color.Gray) // Añade un borde gris a la Card
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.estudiante), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el espacio de la tarjeta
                        .align(Alignment.Center) // Centra la imagen
                        .clip(RoundedCornerShape(8.dp)) // Aplica bordes redondeados a la imagen
                        .graphicsLayer(alpha = 0.7f), // Establece la transparencia de la imagen
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espaciado interno de la Card
                        .align(Alignment.BottomStart) // Alinea el Row en la parte inferior
                    , verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    TextButton(
                        onClick = { /* Navegar a Sección 1 */ },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2AA345))
                    ) {
                        Text(text = "Registro extendido")
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // Establece un ancho máximo para la Card
                .heightIn(max = 100.dp)
                .padding(8.dp), // Espaciado alrededor de la Card
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            //  border = BorderStroke(2.dp, Color.Gray) // Añade un borde gris a la Card
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.estudiante), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ocupa todo el espacio de la tarjeta
                        .align(Alignment.Center) // Centra la imagen
                        .clip(RoundedCornerShape(8.dp)) // Aplica bordes redondeados a la imagen
                        .graphicsLayer(alpha = 0.7f), // Establece la transparencia de la imagen
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Espaciado interno de la Card
                        .align(Alignment.BottomStart) // Alinea el Row en la parte inferior
                    , verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    TextButton(
                        onClick = { /* Navegar a Sección 1 */ },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2AA345))
                    ) {
                        Text(text = "Otros")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNavigate("Login") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4EA836), // Color de fondo
                contentColor = Color.White // Color del texto
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(150.dp)
                .offset(y = (-32).dp) // Mueve el botón hacia arriba 16 dp
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) // Agrega sombra
                .clip(RoundedCornerShape(12.dp)), // Bordes redondeados
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        ) {
            Text(
                text = "Cerrar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold, // Cambia el peso a Bold para mayor énfasis
                modifier = Modifier.padding(4.dp) // Añade un poco de espaciado interno
            )
        }

    }
}