package com.example.apinasaapp.di

import com.example.apinasaapp.MainActivity
import com.example.apinasaapp.ui.home.HomeFragment
import com.example.apinasaapp.ui.imagevideodetails.ImageVideoDetailsFragment
import com.example.apinasaapp.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment


    @ContributesAndroidInjector
    abstract fun contributeImageVideoDetailsFragment(): ImageVideoDetailsFragment
}