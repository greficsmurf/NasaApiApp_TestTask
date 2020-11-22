package com.example.apinasaapp.models

import com.squareup.moshi.Json

data class AssetItem(
    @Json(name = "href")
    val link: String
)