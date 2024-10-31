package com.example.proyectofinal.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "solicitantes",
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = ["idUsuario"],
        childColumns = ["creadoPor"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Solicitante(
    @PrimaryKey(autoGenerate = true)
    val idSolicitante: Int = 0,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val creadoPor: Int // FK hacia Usuario
)
