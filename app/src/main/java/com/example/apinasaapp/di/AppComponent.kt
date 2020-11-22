package com.example.apinasaapp.di

import android.app.Application
import com.example.apinasaapp.NasaApiApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            MainActivityModule::class,
            ViewModelModule::class,
            AppModule::class
        ]
)
interface AppComponent{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: NasaApiApp)
}