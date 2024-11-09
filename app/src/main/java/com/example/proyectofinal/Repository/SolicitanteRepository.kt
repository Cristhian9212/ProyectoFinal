package com.example.proyectofinal.Repository

import com.example.proyectofinal.DAO.SolicitanteDao
import com.example.proyectofinal.Model.Solicitante

class SolicitanteRepository(private val solicitanteDao: SolicitanteDao) {
    suspend fun insertar(solicitante: Solicitante) {
        solicitanteDao.insertar(solicitante)
    }

    suspend fun obtenerTodosSolicitantes(): List<Solicitante> {
        return solicitanteDao.obtenerTodosSolicitantes()
    }

    suspend fun actualizar(solicitante: Solicitante) {
        solicitanteDao.actualizar(solicitante)
    }

    suspend fun eliminar(solicitante: Solicitante) {
        solicitanteDao.eliminar(solicitante)
    }
}
