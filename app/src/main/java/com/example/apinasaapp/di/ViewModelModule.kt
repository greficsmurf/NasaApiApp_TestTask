package com.example.apinasaapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apinasaapp.ui.home.HomeViewModel
import com.example.apinasaapp.ui.imagevideodetails.ImageVideoDetailsViewModel
import com.example.apinasaapp.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(vm: SearchViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ImageVideoDetailsViewModel::class)
    abstract fun bindImageVideoDetailsViewModel(vm: ImageVideoDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}