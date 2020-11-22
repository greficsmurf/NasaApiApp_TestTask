package com.example.apinasaapp

import android.app.Activity
import android.app.Application
import com.example.apinasaapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class NasaApiApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector


}