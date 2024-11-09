package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.proyectofinal.Model.Solicitante
import com.example.proyectofinal.Model.SolicitanteConUsuario

@Dao
interface SolicitanteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(solicitante: Solicitante)

    @Query("SELECT * FROM solicitantes")
    suspend fun obtenerTodosSolicitantes(): List<Solicitante>

    @Update
    suspend fun actualizar(solicitante: Solicitante)

    @Delete
    suspend fun eliminar(solicitante: Solicitante)

    @Transaction
    @Query("SELECT * FROM solicitantes")
    suspend fun obtenerSolicitantesConUsuarios(): List<SolicitanteConUsuario>
}
