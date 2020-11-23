package com.example.apinasaapp.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.apinasaapp.repo.SearchRepo
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
) : ViewModel(){

    private val _searchText = MutableLiveData<String>()

    val pagingData = _searchText.switchMap {
        LiveDataReactiveStreams.fromPublisher(searchRepo.searchPager(it))
    }.cachedIn(viewModelScope)

    fun setSearchText(text: String) {
        if(_searchText.value != text && text.isNotBlank())
            _searchText.postValue(text)
    }

    val searchText: String
        get() = _searchText.value ?: ""

}