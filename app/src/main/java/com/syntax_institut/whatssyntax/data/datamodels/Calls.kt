package com.syntax_institut.whatssyntax.data.datamodels

import com.squareup.moshi.Json

data class Calls(

    @Json(name = "contact")
    val contact: Contact,

    @Json(name = "incoming")
    val incoming: Boolean,

    @Json(name = "accepted")
    val accepted: Boolean,

    @Json(name = "time")
    val time: String
)
