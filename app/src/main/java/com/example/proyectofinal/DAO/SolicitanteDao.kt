package com.example.proyectofinal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofinal.Model.Solicitante

@Dao
interface SolicitanteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(solicitante: Solicitante)

    @Query("SELECT * FROM solicitantes")
    suspend fun obtenerTodosSolicitantes(): List<Solicitante>
}
