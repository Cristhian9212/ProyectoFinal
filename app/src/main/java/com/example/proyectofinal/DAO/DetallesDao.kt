package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.proyectofinal.Model.Prestamo
import com.example.proyectofinal.Model.PrestamoConDetalles

@Dao
interface DetallesDao {

    // Obtener los detalles de los préstamos con relaciones
    @Transaction
    @Query("SELECT * FROM prestamos")
    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles>

    // Eliminar un préstamo específico
    @Delete
    suspend fun eliminarPrestamo(prestamo: Prestamo)

    // Actualizar un préstamo específico
    @Update
    suspend fun actualizarPrestamo(prestamo: Prestamo)
}
