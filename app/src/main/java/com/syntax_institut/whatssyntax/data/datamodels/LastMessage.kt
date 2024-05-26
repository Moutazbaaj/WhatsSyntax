package com.syntax_institut.whatssyntax.data.datamodels

import com.squareup.moshi.Json

data class LastMessage(
    @Json(name = "text")
    val text: String,

    @Json(name = "incoming")
    val incoming: Boolean
)
