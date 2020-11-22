package com.example.apinasaapp.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.apinasaapp.vo.Resource
import java.lang.Exception

/**
 *  Gets drawable by its attribute reference
 */
fun getDrawableByRef(context: Context, attr: Int): Drawable {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    val imageResId = typedValue.resourceId
    return ContextCompat.getDrawable(context, imageResId) ?: throw IllegalArgumentException("Cannot load drawable $imageResId")
}
fun NavController.navigateSafe(direction: NavDirections){
    try {
        navigate(direction)
    }catch (e: Exception){
    }
}
fun getResourceByLoadStates(loadStates: CombinedLoadStates): Resource<Nothing> {
    when {
        loadStates.prepend is LoadState.Loading -> return Resource.loading(null)
        loadStates.append is LoadState.Loading -> return Resource.loading(null)
        loadStates.refresh is LoadState.Loading -> return Resource.loading(null)
    }
    when {
        loadStates.prepend is LoadState.Error -> return Resource.failed(null)
        loadStates.append is LoadState.Error -> return Resource.failed(null)
        loadStates.refresh is LoadState.Error -> return Resource.failed(null, msg = (loadStates.refresh as LoadState.Error).error.localizedMessage ?: "")
    }

    return Resource.success(null)
}

 fun isImage(link: String): Boolean{
    return "([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.jpg|.png|.jpeg)".toRegex().find(link) != null
}

 fun isVideo(link: String): Boolean{
    return "([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.mp4)".toRegex().find(link) != null
}

