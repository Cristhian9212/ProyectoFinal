package com.example.proyectofinal

import InterfazInicialScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.DAO.DetallesDao
import com.example.proyectofinal.Database.AppDatabase
import com.example.proyectofinal.Model.Computador
import com.example.proyectofinal.Model.Prestamo
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Model.Usuario
import com.example.proyectofinal.Repository.ComputadorRepository
import com.example.proyectofinal.Repository.DetallesRepository
import com.example.proyectofinal.Repository.PrestamoRepository
import com.example.proyectofinal.Repository.SolicitanteRepository
import com.example.proyectofinal.Repository.UsuarioRepository
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
    val ComputadorDao = database.computadorDao()
    val ComputadorRepository = ComputadorRepository(ComputadorDao)
    val SolicitanteDao = database.solicitanteDao()
    val SolicitanteRepository = SolicitanteRepository(SolicitanteDao)
    val PrestamoDao = database.prestamoDao()
    val prestamoRepository = PrestamoRepository(PrestamoDao)
    val DetallesDao = database.detallesDao()
    val DetallesRepository = DetallesRepository(DetallesDao)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                usuarioRepository = usuarioRepository,
                coroutineScope = coroutineScope,
                onLoginSuccess = { usuario ->
                    navController.navigate("interfaz_inicial") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginError = { error -> /* Manejo de errores */ },
                navController = navController
            )
        }
        composable("registro") {
            RegistroAdminScreen { nombres, apellidos, nombreUsuario, cargo, correo, contrasena ->
                val nuevoUsuario = Usuario(
                    nombres = nombres,
                    apellidos = apellidos,
                    nombreUsuario = nombreUsuario,
                    cargo = cargo,
                    correo = correo,
                    contrasena = contrasena
                )
                coroutineScope.launch {
                    usuarioRepository.insertar(nuevoUsuario)
                }
            }
        }
        composable("interfaz_inicial") {
            InterfazInicialScreen(navController = navController) // Pasar el navController aquí
        }
        composable("registrosequipos") {
            RegistrosequiposScreen(
                navController = navController,
                onSaveEquipo = { marca, modelo, numeroSerie, estado ->
                    val nuevoEquipo = Computador(
                        marca = marca,
                        modelo = modelo,
                        numeroSerie = numeroSerie,
                        estado = estado
                    )
                    coroutineScope.launch {
                        ComputadorRepository.insertar(nuevoEquipo)
                    }
                }
            )
        }
        composable("registrosolicitante") {
            RegistroSolicitanteScreen(
                navController = navController,
                usuarioRepository = usuarioRepository,
                onSaveEquipo = { nombre, apellido, correo, telefono, idUsuario -> // Asegúrate de recibir idUsuarioCreadoPor
                    val nuevoSolicitante = Solicitante(
                        nombre = nombre,
                        apellido = apellido,
                        correo = correo,
                        telefono = telefono,
                        idUsuario = idUsuario// Aquí pasas el idUsuario
                    )
                    coroutineScope.launch {
                        SolicitanteRepository.insertar(nuevoSolicitante)
                    }
                }
            )
        }
        composable("registro-prestamo") {
            RegistroPrestamosScreen(
                navController = navController,
                solicitanteRepository = SolicitanteRepository,
                computadorRepository = ComputadorRepository,
                onSaveEquipo = { idSolicitante, idComputador, fechaPrestamo, fechaDevolucion, fechaDevuelta ->
                    val nuevoPrestamo = Prestamo(
                        idSolicitante = idSolicitante,
                        idComputador = idComputador,
                        fechaPrestamo = fechaPrestamo,
                        fechaDevolucion = fechaDevolucion,
                        fechaDevuelta = fechaDevuelta
                    )
                    coroutineScope.launch {
                        prestamoRepository.insertar(nuevoPrestamo) // Asegúrate de usar prestamoRepository aquí
                    }
                }
            )
        }
        composable("interfaz-listar") {
            ListarScreen(navController = navController) // Pasar el navController aquí
        }
        composable("interfaz-listarusuarios") {
            listarUsuarios(
                navController = navController,
                usuarioRepository = usuarioRepository // Pasa usuarioRepository aquí
            )
        }
        composable("interfaz-listarestudiantes") {
            ListarSolicitantes(
                navController = navController,
                solicitanteRepository = SolicitanteRepository,
                usuarioRepository = usuarioRepository
            ) // Pasar el navController aquí
        }
        composable("interfaz-listarcomputadores") {
            ListarComputadores(
                navController = navController,
                computadorRepository = ComputadorRepository
            ) // Pasar el navController aquí
        }
        composable("interfaz-listarprestamos") {
            ListarPrestamos(
                navController = navController,
                detallesRepository = DetallesRepository
            ) // Pasar el navController aquí
        }
    }
}