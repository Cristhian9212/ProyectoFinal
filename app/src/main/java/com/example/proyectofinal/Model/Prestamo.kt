package com.example.proyectofinal.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamos",
    foreignKeys = [
        ForeignKey(entity = Solicitante::class, parentColumns = ["idSolicitante"], childColumns = ["idSolicitante"]),
        ForeignKey(entity = Computador::class, parentColumns = ["idComputador"], childColumns = ["idComputador"])
    ]
)
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val idPrestamo: Int = 0,
    val idSolicitante: Int, // FK hacia Solicitante
    val idComputador: Int, // FK hacia Computador
    val fechaPrestamo: String,
    val fechaDevolucion: String, //fecha maxima en la que se tiene que hacer la devolucion del equipo
    val fechaDevuelta: String? = null //Fecha en la que realmente se esta devolviendo el equipo
)
