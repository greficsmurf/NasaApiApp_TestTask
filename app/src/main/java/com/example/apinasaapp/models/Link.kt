package com.example.apinasaapp.models

import com.squareup.moshi.Json

data class Link(
    val rel: String,
    @Json(name = "href")
    val link: String,
    val render: String
)