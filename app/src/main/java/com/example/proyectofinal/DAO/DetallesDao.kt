package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyectofinal.Model.PrestamoConDetalles

@Dao
interface DetallesDao {
    @Transaction
    @Query("SELECT * FROM prestamos")
    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles>
}
