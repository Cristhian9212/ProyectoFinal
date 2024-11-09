import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.R
import com.example.proyectofinal.Screen.DrawerContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterfazInicialScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Obtener el contexto fuera del bloque clickable
    val context = LocalContext.current

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
                        .width(300.dp) // Define el ancho del Drawer
                        .background(Color.White)
                ) {
                    DrawerContent(onNavigate)
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true // Permite que se cierre tocando fuera del Drawer
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
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            },
            content = { paddingValues -> // Aquí es donde usamos paddingValues
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Aplica el padding del Scaffold
                ) {
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Bienvenido",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Plataforma de gestión de préstamos",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )

                        val images = listOf(
                            R.drawable.iniciovertical1,
                            R.drawable.iniciovertical2,
                            R.drawable.iniciovertical3)

                        val descriptions = listOf(
                            "Para mayor información acercarse a la oficina de Bienestar Universitario,\n" +
                                    "o escribir al correo:\n" +
                                    "permanencia.bienestar.udec@ucundinamarca.edu.co",
                            "La Dirección de Talento Humano invita a participar en las convocatorias laborales vigentes,\n" +
                                    "según los perfiles requeridos por los diferentes direcciones, oficinas, dependencias\n" +
                                    "y/o programas académicos.",
                            "Estimados Gestores de Oportunidades,\n" +
                                    "Desde Bienestar Universitario extendemos la invitación a participar en la encuesta de identificación de necesidades que hemos construido con el objetivo de conocer de manera más precisa sus necesidades y expectativas en relación con los servicios, programas y espacios para el fortalecimiento de la calidad de vida, hábitos saludables y formación para la vida."
                        )

                        val urls = listOf(
                            "https://www.ucundinamarca.edu.co/documents/varios/2024/Infografia-PSE-2025-1.pdf",
                            "https://www.ucundinamarca.edu.co/index.php/convocatorias-laborales",
                            "https://forms.office.com/Pages/ResponsePage.aspx?id=oGfaB0MfjE6Xf1-ItkcO5qRKry4dtgRHtDDDJ8l-MdZUMVRUNjlNWFY3WVNTRjhUQjJNQUk5T0I2Si4u&origin=QRCode"
                        )

                        var currentIndex by remember { mutableStateOf(0) }

                        LaunchedEffect(Unit) {
                            while (true) {
                                delay(10000) // 10 segundos
                                currentIndex = (currentIndex + 1) % images.size
                            }
                        }

                        val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

                        val selectedImage = if (isPortrait) {
                            listOf(R.drawable.iniciovertical1, R.drawable.iniciovertical2, R.drawable.iniciovertical3)
                        } else {
                            listOf(R.drawable.iniciohorizontal1, R.drawable.iniciohorizontal2, R.drawable.iniciohorizontal3)
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            item {
                                Image(
                                    painter = painterResource(id = selectedImage[currentIndex]),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .height(300.dp)
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    text = descriptions[currentIndex],
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .background(Color.White.copy(alpha = 0.5f), shape = MaterialTheme.shapes.medium)
                                        .padding(8.dp)
                                )
                                // Imagen adicional que cambia cada 10 segundos
                                val additionalImages = listOf(
                                    R.drawable.inicio1,
                                    R.drawable.inicio2,
                                    R.drawable.inicio3)

                                var additionalIndex by remember { mutableStateOf(0) }

                                // Cambiar imagen cada 10 segundos
                                LaunchedEffect(Unit) {
                                    while (true) {
                                        delay(10000) // 10 segundos
                                        additionalIndex = (additionalIndex + 1) % additionalImages.size
                                    }
                                }

                                Text(
                                    text = "Para más información escanea el código QR.",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.5f), shape = MaterialTheme.shapes.medium)
                                        .padding(8.dp)
                                )

                                Text(
                                    text = "O haz click aquí",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .clickable {
                                            val url = urls[currentIndex]
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                            context.startActivity(intent)
                                        }
                                )

                                // Mostrar la imagen adicional
                                Image(
                                    painter = painterResource(id = additionalImages[additionalIndex]),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .sizeIn(maxHeight = 150.dp) // Tamaño máximo de altura sin recortar
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Fit // Ajusta la imagen para que quepa sin distorsionarse
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
