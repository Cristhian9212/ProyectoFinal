package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.PrestamoDao
import com.example.proyectofinal.Model.Prestamo

class PrestamoRepository(private val prestamoDao: PrestamoDao) {
    suspend fun insertar(prestamo: Prestamo) {
        prestamoDao.insertar(prestamo)
    }

    suspend fun obtenerPrestamosPorSolicitante(): List<Prestamo> {
        return prestamoDao.obtenerPrestamosPorSolicitante()
    }
}
