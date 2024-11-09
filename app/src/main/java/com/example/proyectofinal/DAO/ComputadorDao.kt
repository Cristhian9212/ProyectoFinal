package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal.Model.Computador

@Dao
interface ComputadorDao {

    // Método para insertar un computador
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(computador: Computador)

    // Método para actualizar el estado de un computador (Disponible o Prestado)
    @Query("UPDATE computadores SET estado = :estado WHERE idComputador = :idComputador")
    suspend fun actualizarEstado(idComputador: Int, estado: String)

    // Método para obtener todos los computadores
    @Query("SELECT * FROM computadores")
    suspend fun obtenerTodosLosComputadores(): List<Computador>

    // Método para eliminar un computador
    @Delete
    suspend fun eliminar(computador: Computador)

    // Método para actualizar un computador
    @Update
    suspend fun actualizar(computador: Computador)
}
