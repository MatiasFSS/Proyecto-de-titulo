package com.copetiny.proyecto

import android.app.Application
import com.copetiny.proyecto.ui.register.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProyectoApp:Application(){

    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}