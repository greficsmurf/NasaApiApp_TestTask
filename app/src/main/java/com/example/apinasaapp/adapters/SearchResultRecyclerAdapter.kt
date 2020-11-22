package com.example.apinasaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apinasaapp.R
import com.example.apinasaapp.databinding.VhImageVideoItemBinding
import com.example.apinasaapp.models.ImageVideoItem

const val IMAGE_REL = "preview"

class SearchResultRecyclerAdapter(
    private val navigateToDetails: (nasaId: String) -> Unit
) : PagingDataAdapter<ImageVideoItem, RecyclerView.ViewHolder>(diffUtil){
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ImageVideoItem>(){
            override fun areItemsTheSame(
                oldItem: ImageVideoItem,
                newItem: ImageVideoItem
            ): Boolean = oldItem.link == newItem.link

            override fun areContentsTheSame(
                oldItem: ImageVideoItem,
                newItem: ImageVideoItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: VhImageVideoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.vh_image_video_item,
            parent,
            false
        )


        return ImageVideoItemViewHolder(binding, navigateToDetails)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ImageVideoItemViewHolder)
            holder.bind(getItem(position))
    }
}

class ImageVideoItemViewHolder(
    private val binding: VhImageVideoItemBinding,
    private val navigateToDetails: (nasaId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root){

    fun bind(imageVideoItem: ImageVideoItem?){
        imageVideoItem?.let {item ->
            val data = item.data.firstOrNull()
            val previewImage = item.links.firstOrNull {
                it.rel == IMAGE_REL
            }

            data?.let {dataItem ->
                binding.apply {
                    title.text = dataItem.title
                    description.text = dataItem.description
                    image.visibility = View.VISIBLE
                    holder.setOnClickListener {
                        navigateToDetails.invoke(dataItem.nasaId)
                    }
                    executePendingBindings()

                    previewImage?.let { link ->
                        setImage(image, link.link)
                    } ?: image.apply {
                        visibility = View.GONE
                    }

                }
            }
        }
    }
}