package com.example.apinasaapp.api

import com.example.apinasaapp.api.models.AssetWrapperApi
import com.example.apinasaapp.api.models.ImageVideoCollectionWrapperApi
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService{

    @GET("search")
    fun search(@Query("q") searchText: String, @Query("page") page: Int = 1): Observable<ImageVideoCollectionWrapperApi>

    @GET("search")
    fun searchById(@Query("nasa_id") nasaId: String) : Observable<ImageVideoCollectionWrapperApi>

    @GET("asset/{nasaId}")
    fun getAssetsById(@Path("nasaId") nasaId: String) : Observable<AssetWrapperApi>
}