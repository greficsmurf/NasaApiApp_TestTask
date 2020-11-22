package com.example.apinasaapp.adapters

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.apinasaapp.R
import com.example.apinasaapp.vo.ResourceStatus


@BindingAdapter("android:isVisible")
fun setVisibility(view: View, isVisible: Boolean){
    if(isVisible)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.LOADED -> setVisibility(view, false)
        ResourceStatus.FAILED -> setVisibility(view, false)
        ResourceStatus.LOADING -> setVisibility(view, true)
        ResourceStatus.AUTH_REJECTED -> setVisibility(view, false)
        null -> setVisibility(view, false)
    }
}

@BindingAdapter("android:isErrorVisible")
fun setErrorVisibility(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.FAILED -> setVisibility(view, true)
        else -> setVisibility(view, false)
    }
}

@BindingAdapter("android:isAuthVisible")
fun setAuthVisible(view: View, resourceStatus: ResourceStatus?){
    when(resourceStatus){
        ResourceStatus.AUTH_REJECTED -> setVisibility(view, true)
        else -> setVisibility(view, false)
    }
}
@BindingAdapter("android:imageSrc")
fun setImage(view: ImageView, imageLink: String){
    Glide
        .with(view)
        .load(imageLink)
        .apply {
            apply(
                RequestOptions.formatOf(DecodeFormat.PREFER_RGB_565)
            )
            transform(
                CenterCrop(),
                RoundedCorners(14)
            )

        }
        .placeholder(ContextCompat.getDrawable(view.context, R.drawable.ic_wallpaper_24px))
        .error(ContextCompat.getDrawable(view.context, R.drawable.ic_broken_image_24px))
        .into(view)
}

@BindingAdapter("android:imageOrig")
fun setFullImage(view: ImageView, imageLink: String){
    Glide
        .with(view)
        .load(imageLink)
        .placeholder(R.drawable.ic_wallpaper_24px)
        .error(R.drawable.ic_broken_image_24px)
        .into(view)
}