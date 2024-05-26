package com.syntax_institut.whatssyntax.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syntax_institut.whatssyntax.data.datamodels.Note


// Database class representing the local SQLite database
@Database(entities = [Note::class], version = 3, exportSchema = false)
abstract class WhatsSyntaxDatabase : RoomDatabase() {
    abstract val databaseDao: WhatsSyntaxDatabaseDao
}

// Singleton pattern to ensure only one instance of the database is created
private lateinit var INSTANCE: WhatsSyntaxDatabase


// Function to get an instance of the database
fun getDatabase(context: Context): WhatsSyntaxDatabase {
    synchronized(WhatsSyntaxDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                WhatsSyntaxDatabase::class.java,
                "notes_database"
            ).build() //.fallbackToDestructiveMigration()
        }
    }
    return INSTANCE
}




