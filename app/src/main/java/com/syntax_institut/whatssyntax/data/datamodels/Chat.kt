package com.syntax_institut.whatssyntax.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Chat(
    @Json(name = "id")
    val id: Int,

    @Json(name = "contact")
    val contact: Contact,

    @Json(name = "lastMessage")
    val lastMessage: LastMessage,

)
