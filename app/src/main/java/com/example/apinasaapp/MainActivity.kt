package com.example.apinasaapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private val appBarConfig: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    private val toolbar: Toolbar by lazy{
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.home_title)
        toolbar.setupWithNavController(navController, appBarConfig)
    }

    fun hideToolbar(){
        supportActionBar?.hide()
    }

    fun showToolbar(){
        supportActionBar?.show()
    }


    override fun supportFragmentInjector() = dispatchingAndroidInjector
}