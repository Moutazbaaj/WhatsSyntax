package com.syntax_institut.whatssyntax.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val text: String
)