package com.example.proyectofinal.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "computadores")
data class Computador(
    @PrimaryKey(autoGenerate = true)
    val idComputador: Int = 0,
    val marca: String,
    val modelo: String,
    val numeroSerie: String,
    val estado: String // Disponible o Prestado
)
