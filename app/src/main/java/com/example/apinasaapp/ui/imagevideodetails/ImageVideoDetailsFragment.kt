package com.example.apinasaapp.ui.imagevideodetails

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apinasaapp.MainActivity
import com.example.apinasaapp.R
import com.example.apinasaapp.adapters.AssetRecyclerAdapter
import com.example.apinasaapp.base.BaseFragment
import com.example.apinasaapp.databinding.FragmentImageVideoDetailsBinding
import com.example.apinasaapp.util.navigateSafe
import javax.inject.Inject

class ImageVideoDetailsFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: ImageVideoDetailsViewModel by viewModels {
        vmFactory
    }

    private val args: ImageVideoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentImageVideoDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image_video_details,
            container,
            false
        )

        val assetAdapter = AssetRecyclerAdapter(
            navigateToAssetViewer = {
                findNavController().navigateSafe(
                    ImageVideoDetailsFragmentDirections.actionImageVideoDetailsFragmentToAssetViewerBottomDialogFragment(
                        it
                    )
                )
            }
        )
        vm.setNasaId(args.nasaId)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            assetsRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = assetAdapter
            }

            vm.assets.observe(viewLifecycleOwner, Observer {
                assetAdapter.submitList(it)
            })

            vm.detailsResource.observe(viewLifecycleOwner, Observer { res ->
                resource = res
            })
            vm.data.observe(viewLifecycleOwner, Observer { data ->
                data?.let {
                    title.text = it.title
                    description.text = it.description
                }
            })
            description.apply {
                val mMaxLines = 6
                var mIsTruncated = true
                val mEllipsize = TextUtils.TruncateAt.END

                ellipsize = mEllipsize
                maxLines = mMaxLines

                setOnClickListener {
                    if(mIsTruncated)
                    {
                        ellipsize = null
                        maxLines = Int.MAX_VALUE
                        mIsTruncated = false
                    }
                    else
                    {
                        ellipsize = mEllipsize
                        maxLines = mMaxLines
                        mIsTruncated = true
                    }
                }
            }

        }



        return binding.root
    }
}