package com.example.apinasaapp.api.models

import com.squareup.moshi.Json

data class ImageVideoItemApi(
    val data: List<ImageVideoDataApi>?,
    @Json(name = "href")
    val link: String?,
    val links: List<LinkApi>?
)