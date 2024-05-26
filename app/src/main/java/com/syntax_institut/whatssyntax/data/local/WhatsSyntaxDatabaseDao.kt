package com.syntax_institut.whatssyntax.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.data.datamodels.Note


// Data Access Object (DAO) interface for interacting with the local database
@Dao
interface WhatsSyntaxDatabaseDao {


    // Insert a note into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)


    // Get all notes from the database
    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<Note>>


    // Delete a note from the database by its ID
    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteById(id: Long)


    // Delete all notes from the database
    @Query("DELETE FROM Note")
    suspend fun deleteAll()


}