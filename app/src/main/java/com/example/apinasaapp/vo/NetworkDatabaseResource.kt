package com.example.apinasaapp.vo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception

abstract class NetworkDatabaseResource<T> {
    private val result = MutableLiveData<Resource<T>>()

    init {
        result.postValue(Resource.loading(null))
        try{
            fetchDb().subscribe { dbData ->
                if(shouldFetchApi(dbData)){
                    Timber.d("fetched API")
                    fetchApi().subscribe { apiData ->
                        onDbSave(apiData)
                    }
                }
                else
                {
                    Timber.d("fetched DATABASE")
                    result.postValue(Resource.success(dbData))
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
            result.postValue(Resource.failedEx(e))
        }
    }

    fun asLiveData() = result as LiveData<Resource<T>>

    abstract fun fetchDb(): Maybe<T>

    abstract fun fetchApi(): Observable<T>

    abstract fun shouldFetchApi(data: T?): Boolean
    abstract fun onDbSave(data: T)

    protected open fun onError(e: Exception): Resource<T> = Resource.failedEx(e)
}