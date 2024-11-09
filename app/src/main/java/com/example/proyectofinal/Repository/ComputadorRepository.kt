package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.ComputadorDao
import com.example.proyectofinal.Model.Computador

class ComputadorRepository(private val computadorDao: ComputadorDao) {

    // Método para insertar un computador
    suspend fun insertar(computador: Computador) {
        computadorDao.insertar(computador)
    }

    // Método para obtener todos los computadores
    suspend fun obtenerTodosLosComputadores(): List<Computador> {
        return computadorDao.obtenerTodosLosComputadores()
    }

    // Método para actualizar el estado de un computador
    suspend fun actualizarEstado(idComputador: Int, estado: String) {
        computadorDao.actualizarEstado(idComputador, estado)
    }

    // Método para eliminar un computador
    suspend fun eliminar(computador: Computador) {
        computadorDao.eliminar(computador)
    }

    // Método para actualizar un computador
    suspend fun actualizar(computador: Computador) {
        computadorDao.actualizar(computador)
    }
}
