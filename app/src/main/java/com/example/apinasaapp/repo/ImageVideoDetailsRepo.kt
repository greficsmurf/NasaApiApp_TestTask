package com.example.apinasaapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.apinasaapp.api.NasaApiService
import com.example.apinasaapp.models.AssetItem
import com.example.apinasaapp.models.ImageVideoItem
import com.example.apinasaapp.models.mappers.toDomainModel
import com.example.apinasaapp.vo.NetworkResource
import com.example.apinasaapp.vo.Resource
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageVideoDetailsRepo @Inject constructor(
    private val nasaApiService: NasaApiService
) {

    fun getDetails(nasaId: String): LiveData<Resource<ImageVideoItem>> = object : NetworkResource<ImageVideoItem>(){
        override fun fetch(): Observable<ImageVideoItem> {
            return nasaApiService.searchById(nasaId).map { col -> col.collection.items.firstOrNull()?.toDomainModel() }
        }
    }.asLiveData()

    fun getAssets(nasaId: String): LiveData<List<AssetItem>> {
        return LiveDataReactiveStreams.fromPublisher(
            nasaApiService.getAssetsById(nasaId).map { wrapper ->
                wrapper.collection.items.map { it.toDomainModel() }
            }.toFlowable(BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
        )
    }

}