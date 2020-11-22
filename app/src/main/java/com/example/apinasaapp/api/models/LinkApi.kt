package com.example.apinasaapp.api.models

import com.squareup.moshi.Json

data class LinkApi(
    val rel: String?,
    @Json(name = "href")
    val link: String?,
    val render: String?
)