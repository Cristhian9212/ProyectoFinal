package com.example.proyectofinal.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int = 0,
    val nombreUsuario: String,
    val contrasena: String,
    val correo: String
)
