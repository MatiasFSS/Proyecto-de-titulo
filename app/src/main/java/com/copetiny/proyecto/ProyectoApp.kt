package com.copetiny.proyecto

import android.app.Application
import com.copetiny.proyecto.ui.register.PrefsUsers
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProyectoApp:Application(){

    companion object{
        lateinit var prefs:PrefsUsers
    }
    override fun onCreate() {
        super.onCreate()
        prefs = PrefsUsers(applicationContext)
    }
}