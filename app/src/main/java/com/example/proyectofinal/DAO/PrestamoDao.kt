package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofinal.Model.Prestamo

@Dao
interface PrestamoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos WHERE idSolicitante = :idSolicitante")
    suspend fun obtenerPrestamosPorSolicitante(idSolicitante: Int): List<Prestamo>
}
