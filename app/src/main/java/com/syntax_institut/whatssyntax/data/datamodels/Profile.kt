package com.syntax_institut.whatssyntax.data.datamodels

import com.squareup.moshi.Json

data class Profile(
    @Json(name = "name")
    val name: String,

    @Json(name = "number")
    val number: String,

    @Json(name = "image")
    var image: String
)

