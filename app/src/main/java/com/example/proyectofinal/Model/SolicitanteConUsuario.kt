package com.example.proyectofinal.Model

import androidx.room.Embedded
import androidx.room.Relation

data class SolicitanteConUsuario(
    @Embedded val solicitante: Solicitante,
    @Relation(
        parentColumn = "idUsuario",
        entityColumn = "idUsuario"
    )
    val usuario: Usuario
)
