package com.example.proyectofinal.Model

import androidx.room.Embedded
import androidx.room.Relation

data class PrestamoConDetalles(
    @Embedded val prestamo: Prestamo,
    @Relation(
        parentColumn = "idSolicitante",
        entityColumn = "idSolicitante"
    )
    val solicitante: Solicitante,
    @Relation(
        parentColumn = "idComputador",
        entityColumn = "idComputador"
    )
    val computador: Computador
)
