package com.example.apinasaapp.models.mappers

import com.example.apinasaapp.api.models.AssetItemApi
import com.example.apinasaapp.models.AssetItem

fun AssetItemApi.toDomainModel() = AssetItem(
    link
)