package com.example.apinasaapp.ui.imagevideodetails

import androidx.lifecycle.*
import com.example.apinasaapp.repo.ImageVideoDetailsRepo
import javax.inject.Inject

class ImageVideoDetailsViewModel @Inject constructor(
    imageVideoDetailsRepo: ImageVideoDetailsRepo
) : ViewModel(){

    private val _nasaId = MutableLiveData<String>()

    val detailsResource = _nasaId.switchMap { id ->
        imageVideoDetailsRepo.getDetails(id)
    }

    val details = detailsResource.map {resource ->
        resource.data
    }

    val data = details.map { details ->
        details?.data?.firstOrNull()
    }

    val assets = _nasaId.switchMap { id ->
        imageVideoDetailsRepo.getAssets(id)
    }


    fun setNasaId(id: String){
        _nasaId.value = id
    }

}