package com.example.apinasaapp.vo

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.asLiveData
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.flow
import java.lang.Exception

abstract class NetworkResource<T> {
    private val result = Observable.create<Resource<T>> {emitter ->
        emitter.onNext(Resource.loading(null))
        try {
            fetch()?.onErrorComplete {
                it.printStackTrace()
                emitter.onNext(Resource.failedEx(it as Exception))
                true
            }?.subscribe { apiData ->
                emitter.onNext(Resource.success(apiData))
            } ?: emitter.onNext(Resource.failed(null, msg = "no data"))
        }catch (e: Exception){
            e.printStackTrace()
            emitter.onNext(Resource.failedEx(e))
        }
    }.toFlowable(BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())

    fun asLiveData() = LiveDataReactiveStreams.fromPublisher(result)

    abstract fun fetch(): Observable<T>?

    protected open fun onError(e: Exception): Resource<T> = Resource.failedEx(e)
}