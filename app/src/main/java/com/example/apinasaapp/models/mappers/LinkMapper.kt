package com.example.apinasaapp.models.mappers

import com.example.apinasaapp.api.models.LinkApi
import com.example.apinasaapp.models.Link

fun LinkApi.toDomainModel() = Link(
    rel ?: "",
    link ?: "",
    render ?: ""
)

