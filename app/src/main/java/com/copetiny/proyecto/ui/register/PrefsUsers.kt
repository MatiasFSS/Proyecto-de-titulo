package com.copetiny.proyecto.ui.register

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale

class PrefsUsers (val context: Context) {

    val SHARE_NAME = "Mydtb"
    val USER_NAME = "username"
    val USER_AGE = "age"
    val BAR_EXP= "exp"
    val LEVEL = "level"
    val FLAG = "flag"
    val IS_FIRST_RUN ="isFirstRun"
    val LAST_QUESTION_TIMESTAMP = "lastQuestionTimestamp"

    //val USER_IMAGE = "img"


    val storage = context.getSharedPreferences(SHARE_NAME, 0)

    fun saveName(name:String){
        storage.edit().putString(USER_NAME, name).apply()
    }

    fun saveAge(age:Int){
        storage.edit().putInt(USER_AGE,age).apply()
    }

    fun saveExp(exp:Int){
        storage.edit().putInt(BAR_EXP,exp).apply()

    }
    fun saveLevel(level:Int){
        storage.edit().putInt(LEVEL,level).apply()
    }

    fun setDialogFlag(flag:Boolean){
        storage.edit().putBoolean(FLAG,flag).apply()
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

    fun getLevel():Int{
        return storage.getInt(LEVEL, 0)!!
    }

    fun getExp():Int{
        return storage.getInt(BAR_EXP, 0)!!
    }

    fun updialogFlag():Boolean{
        return storage.getBoolean(FLAG, false)
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

    fun isFirstRun():Boolean{
        return storage.getBoolean(IS_FIRST_RUN, true)
    }
    fun setFirstRun(isFirstRun:Boolean){
        with(storage.edit()){
            putBoolean(IS_FIRST_RUN, isFirstRun)
            apply()
        }
    }

    fun getLastQuestionTimestamp():Long{
        return storage.getLong(LAST_QUESTION_TIMESTAMP, 0L)
    }

    fun setLastQuestionTimestamp(timestamp:Long){
        with(storage.edit()){
            putLong(LAST_QUESTION_TIMESTAMP, timestamp)
            apply()
        }
    }


}