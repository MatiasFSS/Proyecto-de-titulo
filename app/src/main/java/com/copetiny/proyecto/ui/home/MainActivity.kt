package com.copetiny.proyecto.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.copetiny.proyecto.ProyectoApp
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityMainBinding
import com.copetiny.proyecto.ui.questionday.QuestionDay
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

        if(prefs.getName().isNotEmpty()){
            initUI()
        }else{
            goToRegister()
        }


    }
    private fun initUI(){
        val isFirstRun = prefs.isFirstRun()
        val lastQuestionTimestamp =prefs.getLastQuestionTimestamp()
        val currentTime = System.currentTimeMillis()

        if(isFirstRun){
            prefs.setFirstRun(false)
        }else{
            if(currentTime - lastQuestionTimestamp >= 24 * 60 * 60 * 1000) {
                startActivity(Intent(this, QuestionDay::class.java))
                prefs.setLastQuestionTimestamp(System.currentTimeMillis())
            }
            //initNavigation()
        }

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