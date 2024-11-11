package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.DetallesDao
import com.example.proyectofinal.Model.Prestamo
import com.example.proyectofinal.Model.PrestamoConDetalles

class DetallesRepository(private val detallesDao: DetallesDao) {

    // Método para obtener los detalles de los préstamos
    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles> {
        return detallesDao.obtenerPrestamosConDetalles()
    }

    // Método para eliminar un préstamo específico
    suspend fun eliminarPrestamo(prestamo: Prestamo) {
        detallesDao.eliminarPrestamo(prestamo)
    }

    // Método para actualizar un préstamo específico
    suspend fun actualizarPrestamo(prestamo: Prestamo) {
        detallesDao.actualizarPrestamo(prestamo)
    }
}
