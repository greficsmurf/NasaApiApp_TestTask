package com.example.apinasaapp.api.models

import com.squareup.moshi.Json
import java.util.*

data class ImageVideoDataApi(
    @Json(name = "nasa_id")
    val nasaId: String,
    val description: String?,
    val title: String?,
    val keywords: List<String>?,
    val center: String?,
    @Json(name = "date_created")
    val dateCreated: Date?,
    val photographer: String?,
    @Json(name = "media_type")
    val mediaType: String?,
    val location: String?
)