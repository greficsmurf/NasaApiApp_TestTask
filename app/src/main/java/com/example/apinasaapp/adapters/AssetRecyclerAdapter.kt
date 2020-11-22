package com.example.apinasaapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apinasaapp.R
import com.example.apinasaapp.models.AssetItem
import com.example.apinasaapp.util.getDrawableByRef
import com.example.apinasaapp.util.isImage
import com.example.apinasaapp.util.isVideo
import com.google.android.material.snackbar.Snackbar
import java.net.URL

class AssetRecyclerAdapter(
    val navigateToAssetViewer: (link: String) -> Unit
) : ListAdapter<AssetItem, RecyclerView.ViewHolder>(diffUtil){
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<AssetItem>(){
            override fun areItemsTheSame(oldItem: AssetItem, newItem: AssetItem): Boolean {
                return oldItem.link == newItem.link
            }

            override fun areContentsTheSame(oldItem: AssetItem, newItem: AssetItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AssetViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.vh_asset_item,
                parent,
                false
            ) as ImageView,
            navigateToAssetViewer
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is AssetViewHolder)
            holder.bind(getItem(position))
    }
}

class AssetViewHolder(
    private val imageView: ImageView,
    private val navigateToAssetViewer: (link: String) -> Unit
) : RecyclerView.ViewHolder(imageView){
    fun bind(item: AssetItem){

        imageView.setImageDrawable(null)
        imageView.background = null

        when {
            isImage(item.link) -> {
                setImage(imageView, item.link.replace("http:", "https:"))
                imageView.setOnClickListener {
                    navigateToAssetViewer.invoke(item.link)
                }
            }
            isVideo(item.link) -> {
                imageView.background = ContextCompat.getDrawable(imageView.context, R.drawable.ic_play_arrow_24px)
                imageView.setOnClickListener {
                    navigateToAssetViewer.invoke(item.link)
                }
            }
            else -> {
                imageView.background = ContextCompat.getDrawable(imageView.context, R.drawable.ic_image_not_supported_24px)
                imageView.setOnClickListener {
                    Snackbar.make(imageView, imageView.context.getString(R.string.unsupported_asset_message), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


}