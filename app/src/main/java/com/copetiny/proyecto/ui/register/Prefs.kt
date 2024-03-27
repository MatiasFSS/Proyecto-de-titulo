package com.copetiny.proyecto.ui.register

import android.content.Context

class Prefs (val context: Context) {

    val SHARE_NAME = "Mydtb"
    val USER_NAME = "username"
    val USER_AGE = "age"
    //val USER_IMAGE = "img"


    val storage = context.getSharedPreferences(SHARE_NAME, 0)

    fun saveName(name:String){
        storage.edit().putString(USER_NAME, name).apply()
    }

    fun saveAge(age:String){
        storage.edit().putString(USER_AGE,age).apply()
    }

    /*fun saveImg(img:String){
        storage.edit().putString(USER_IMAGE, img).commit()
    }*/

    fun getName():String{
        return storage.getString(USER_NAME, "")!!
    }

    fun getAge():String{
        return storage.getString(USER_AGE, "")!!
    }

    /*fun getImg():String{
        return storage.getString(USER_IMAGE, "")!!
    }*/

    fun wipe(){
        storage.edit().clear().apply()
    }

}