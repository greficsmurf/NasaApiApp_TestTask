package com.example.apinasaapp.models.mappers

import com.example.apinasaapp.api.models.ImageVideoItemApi
import com.example.apinasaapp.models.ImageVideoItem

fun ImageVideoItemApi.toDomainModel() = ImageVideoItem(
    data?.map{ it.toDomainModel() } ?: listOf(),
    link ?: "",
    links?.map { it.toDomainModel() } ?: listOf()
)