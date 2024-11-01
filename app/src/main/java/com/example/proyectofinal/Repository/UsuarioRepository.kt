package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.UsuarioDao
import com.example.proyectofinal.Model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun insertar(usuario: Usuario) = usuarioDao.insertar(usuario)

    suspend fun login(nombreUsuario: String, contrasena: String): Usuario? = usuarioDao.login(nombreUsuario, contrasena)

    suspend fun obtenerTodosUsuarios(): List<Usuario> = usuarioDao.obtenerTodosUsuarios()
}