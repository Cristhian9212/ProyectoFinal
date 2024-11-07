package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofinal.Model.Computador

@Dao
interface ComputadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(computador: Computador)

    @Query("SELECT * FROM computadores WHERE estado = :estado")
    suspend fun obtenerComputadoresPorEstado(estado: String): List<Computador>

    @Query("UPDATE computadores SET estado = :estado WHERE idComputador = :idComputador")
    suspend fun actualizarEstado(idComputador: Int, estado: String)

    // Nuevo método para listar todos los computadores
    @Query("SELECT * FROM computadores")
    suspend fun obtenerTodosLosComputadores(): List<Computador>
}

