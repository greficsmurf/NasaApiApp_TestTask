package com.example.apinasaapp.api.models

import com.squareup.moshi.Json

data class AssetItemApi(
    @Json(name = "href")
    val link: String
)