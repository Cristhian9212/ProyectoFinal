package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.Database.AppDatabase
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.UsuarioRepository
import com.example.proyectofinal.UI.RegistroAdminScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val coroutineScope: CoroutineScope = MainScope() // CoroutineScope para el login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme { // Utiliza el tema predeterminado de Material3
                SetupNavigation(coroutineScope)
            }
        }
    }
}

@Composable
fun SetupNavigation(coroutineScope: CoroutineScope) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val usuarioDao = database.usuarioDao()
    val usuarioRepository = UsuarioRepository(usuarioDao)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                usuarioRepository = usuarioRepository,
                coroutineScope = coroutineScope,
                onLoginSuccess = { usuario ->
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginError = { error ->
                    // Manejo de errores (puedes mostrar un mensaje o un toast)
                },
                navController = navController // Aquí se pasa el navController
            )
        }
        composable("home") {
            Text("Bienvenido a la aplicación")
        }
        composable("registro") {
            RegistroAdminScreen { nombreUsuario, contrasena, correo ->
                // Manejo del registro
                val nuevoUsuario = Usuario(nombreUsuario = nombreUsuario, contrasena = contrasena, correo = correo)

                // Insertar el nuevo usuario en la base de datos
                coroutineScope.launch {
                    usuarioRepository.insertar(nuevoUsuario)
                    // Navegar de regreso a la pantalla de login o a donde desees después de registrar
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                }
            }
        }
    }
}
