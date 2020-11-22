package com.example.apinasaapp.models.mappers

import com.example.apinasaapp.api.models.ImageVideoDataApi
import com.example.apinasaapp.api.models.ImageVideoItemApi
import com.example.apinasaapp.models.ImageVideoData
import com.example.apinasaapp.models.ImageVideoItem
import java.util.*

fun ImageVideoDataApi.toDomainModel() = ImageVideoData(
    nasaId,
    description ?: "",
    title ?: "",
    keywords ?: listOf(),
    center ?: "",
    dateCreated ?: Date(),
    photographer ?: "",
    mediaType ?: "",
    location ?: ""
)