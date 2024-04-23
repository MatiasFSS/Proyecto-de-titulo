package com.copetiny.proyecto.ui.register

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale

class PrefsUsers (val context: Context) {

    val SHARE_NAME = "Mydtb"
    val USER_NAME = "username"
    val USER_AGE = "age"
    val BAR_LEVEL= "level"

    //val USER_IMAGE = "img"


    val storage = context.getSharedPreferences(SHARE_NAME, 0)

    fun saveName(name:String){
        storage.edit().putString(USER_NAME, name).apply()
    }

    fun saveAge(age:Int){
        storage.edit().putInt(USER_AGE,age).apply()
    }

    fun saveLevel(level:Int){
        storage.edit().putInt(BAR_LEVEL,level).apply()
    }

    /*fun saveImg(img:String){
        storage.edit().putString(USER_IMAGE, img).commit()
    }*/

    fun getName():String{
        return storage.getString(USER_NAME, "")!!
    }

    fun getAge():Int{
        return storage.getInt(USER_AGE, 0)!!
    }

    /*fun getImg():String{
        return storage.getString(USER_IMAGE, "")!!
    }*/

    fun getLevel():Int{
        return storage.getInt(BAR_LEVEL, 0)!!
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

}