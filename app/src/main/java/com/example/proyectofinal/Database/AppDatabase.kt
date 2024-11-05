package com.example.proyectofinal.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinal.DAO.*
import com.example.proyectofinal.Model.*

@Database(entities = [Usuario::class, Solicitante::class, Computador::class, Prestamo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun solicitanteDao(): SolicitanteDao
    abstract fun computadorDao(): ComputadorDao
    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}