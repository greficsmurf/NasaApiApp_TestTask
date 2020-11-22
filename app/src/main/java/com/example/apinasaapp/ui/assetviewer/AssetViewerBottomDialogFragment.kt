package com.example.apinasaapp.ui.assetviewer

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.apinasaapp.R
import com.example.apinasaapp.adapters.setFullImage
import com.example.apinasaapp.databinding.DialogFragmentAssetViewerBinding
import com.example.apinasaapp.util.isImage
import com.example.apinasaapp.util.isVideo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber


class AssetViewerBottomDialogFragment : BottomSheetDialogFragment(){

    private lateinit var player: SimpleExoPlayer

    private val args: AssetViewerBottomDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(OnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        })
        return dialog
    }
    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogFragmentAssetViewerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_asset_viewer,
            container,
            false
        )

        val link = args.link
        player = SimpleExoPlayer.Builder(requireContext()).build()

        binding.apply {
            layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getWindowHeight())
            if(isImage(link)){
                image.visibility = View.VISIBLE
                video.visibility = View.GONE

                image.height
                setFullImage(
                    image,
                    link.replace("http:", "https:")
                )
            }

            else if (isVideo(link)){
                image.visibility = View.GONE
                video.visibility = View.VISIBLE
                video.player = player
                player.setMediaItem(
                    MediaItem.fromUri(link.replace("http:", "https:"))
                )
                player.prepare()
                player.play()
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }


}