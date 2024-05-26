package com.syntax_institut.whatssyntax.data.datamodels


import com.squareup.moshi.Json


data class Contact(

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name= "number")
    val number: String,

    @Json(name = "image")
    val image: String,

    @Json(name = "status")
    val status: Status? = null,

)
