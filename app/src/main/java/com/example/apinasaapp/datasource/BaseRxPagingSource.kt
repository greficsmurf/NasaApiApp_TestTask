package com.example.apinasaapp.datasource

import androidx.paging.PagingSource
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseRxPagingSource <T> : RxPagingSource<Int, T>() where T: Any{

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, T>> {
        val nextPage = params.key ?: 1

        return fetch(nextPage)
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage + 1
                ) as LoadResult<Int, T>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
            .single(
                LoadResult.Page(
                    data = listOf(),
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage
                )
            )
    }


    abstract fun fetch(nextPage: Int): Observable<List<T>>
}