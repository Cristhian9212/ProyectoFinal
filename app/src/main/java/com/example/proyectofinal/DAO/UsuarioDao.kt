package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofinal.Model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario AND contrasena = :contrasena")
    suspend fun login(nombreUsuario: String, contrasena: String): Usuario?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodosUsuarios(): List<Usuario>
}
