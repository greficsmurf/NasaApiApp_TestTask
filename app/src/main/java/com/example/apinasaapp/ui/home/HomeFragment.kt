package com.example.apinasaapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apinasaapp.R
import com.example.apinasaapp.base.BaseFragment
import com.example.apinasaapp.databinding.FragmentHomeBinding
import com.example.apinasaapp.di.Injectable
import com.example.apinasaapp.util.navigateSafe
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: HomeViewModel by viewModels {
        vmFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
                inflater,
                R.layout.fragment_home,
                container,
                false
        )

        binding.toSearchBtn.setOnClickListener {
            findNavController().navigateSafe(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        return binding.root
    }
}