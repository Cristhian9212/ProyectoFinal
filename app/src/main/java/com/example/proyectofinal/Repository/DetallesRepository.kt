package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.DetallesDao
import com.example.proyectofinal.DAO.PrestamoDao
import com.example.proyectofinal.Model.PrestamoConDetalles

class DetallesRepository(private val DetallesDao: DetallesDao) {
    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles> {
        return DetallesDao.obtenerPrestamosConDetalles()
    }
}
