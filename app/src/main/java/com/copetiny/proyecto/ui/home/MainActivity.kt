package com.copetiny.proyecto.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.copetiny.proyecto.ProyectoApp
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityMainBinding
import com.copetiny.proyecto.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenSplash = installSplashScreen()
        screenSplash.setKeepOnScreenCondition{false}

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ProyectoApp.prefs.getName().isNotEmpty()){
            initUI()
        }else{
            goToRegister()
        }


    }
    private fun initUI(){
        initNavigation()
    }

    private fun initNavigation(){
        val navHost = supportFragmentManager.findFragmentById(R.id.FragmentNavContainer) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

    }

    private fun goToRegister(){
        finish()
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}