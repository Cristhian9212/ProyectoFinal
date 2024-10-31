package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.ComputadorDao
import com.example.proyectofinal.Model.Computador

class ComputadorRepository(private val computadorDao: ComputadorDao) {
    suspend fun insertar(computador: Computador) {
        computadorDao.insertar(computador)
    }

    suspend fun obtenerComputadoresPorEstado(estado: String): List<Computador> {
        return computadorDao.obtenerComputadoresPorEstado(estado)
    }

    suspend fun actualizarEstado(idComputador: Int, estado: String) {
        computadorDao.actualizarEstado(idComputador, estado)
    }
}
