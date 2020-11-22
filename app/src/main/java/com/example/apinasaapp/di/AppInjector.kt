package com.example.apinasaapp.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.apinasaapp.NasaApiApp
import com.google.android.material.transition.MaterialFadeThrough
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector{
    fun init(app: NasaApiApp){
        DaggerAppComponent
                .builder()
                .application(app)
                .build()
                .inject(app)

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }

    fun handleActivity(activity: Activity){
        if(activity is HasSupportFragmentInjector){
            AndroidInjection.inject(activity)
        }
        if(activity is FragmentActivity){
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks(){
                override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                    f.enterTransition = MaterialFadeThrough().apply {
                        duration = 600
                    }
                    f.exitTransition = MaterialFadeThrough().apply {
                        duration = 600
                    }
                    if(f is Injectable)
                        AndroidSupportInjection.inject(f)
                }
            }, true)
        }
    }
}