package com.example.proyectofinal.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int = 0,
    val nombres: String,
    val apellidos: String,
    val nombreUsuario: String,
    val cargo: String,
    val correo: String,
    val contrasena: String,

)
