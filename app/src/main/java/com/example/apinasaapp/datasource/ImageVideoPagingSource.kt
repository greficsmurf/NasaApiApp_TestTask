package com.example.apinasaapp.datasource

import com.example.apinasaapp.api.NasaApiService
import com.example.apinasaapp.models.ImageVideoItem
import com.example.apinasaapp.models.mappers.toDomainModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ImageVideoPagingSource @Inject constructor(
    private val nasaApiService: NasaApiService
) : BaseRxPagingSource<ImageVideoItem>(){

    var searchText: String = ""
        set(value) {
            if(value.isNotBlank())
                field = value
        }

    override fun fetch(nextPage: Int): Observable<List<ImageVideoItem>> {
        return nasaApiService.search(searchText, nextPage)
            .map { wrapper ->
                wrapper.collection.items.map { it.toDomainModel() }
            }
    }

}