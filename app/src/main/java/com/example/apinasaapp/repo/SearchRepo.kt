package com.example.apinasaapp.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.apinasaapp.api.NasaApiService
import com.example.apinasaapp.datasource.ImageVideoPagingSource
import com.example.apinasaapp.models.ImageVideoItem
import com.example.apinasaapp.models.mappers.toDomainModel
import com.example.apinasaapp.vo.NetworkDatabaseResource
import com.example.apinasaapp.vo.NetworkResource
import com.example.apinasaapp.vo.Resource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val imageVideoPagingSource: ImageVideoPagingSource
){

    fun search(q: String): LiveData<Resource<List<ImageVideoItem>>> = object : NetworkResource<List<ImageVideoItem>>(){
        override fun fetch(): Observable<List<ImageVideoItem>> = nasaApiService.search(q).map {
            collection -> collection.collection.items.map {
                it.toDomainModel()
            }
        }
    }.asLiveData()

    fun searchPager(q: String): Flowable<PagingData<ImageVideoItem>>{
        return Pager(
            config = PagingConfig(pageSize = 90, prefetchDistance = 10),
            pagingSourceFactory = {
                imageVideoPagingSource.apply {
                    searchText = q
                }
            }
        ).flowable
    }

}