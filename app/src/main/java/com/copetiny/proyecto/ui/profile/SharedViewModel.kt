package com.copetiny.proyecto.ui.profile

import android.app.Dialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copetiny.proyecto.ProyectoApp
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.data.network.ProyectoApiService
import com.copetiny.proyecto.data.network.response.encyclopedia.MaterialResponse
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
@HiltViewModel
class SharedViewModel @Inject constructor (): ViewModel() {
    private val _expProgress = MutableLiveData<Int>()
    val expProgress:LiveData<Int> = _expProgress
    var current = prefs.getExp()


   init {

       Log.i("mama", current.toString())

       if (current in 0..95) {
           _expProgress.value = current
           //prefs.saveLevel(currentLevel)
           Log.i("mama1 ultimo init", current.toString())
       } else {
           _expProgress.value = 0
           prefs.saveExp(_expProgress.value.toString().toInt())
       }
   }

    fun expProgress(points:Int) {
        val currentProgress = _expProgress.value ?: 0
        val newProgress = currentProgress + points
        Log.i("mama2", newProgress.toString())

        if(newProgress >= 100 && !upDialogFlag()){
            _expProgress.value = 0
            prefs.saveExp(0)
            levelProgress()
            setDialogFlag(true)
            current = prefs.getExp()
            Log.i("mama3", current.toString())

        }else{
            _expProgress.value = newProgress
            prefs.saveExp(newProgress)
            current = prefs.getExp()
            Log.i("mama4 ultimofuncion", current.toString())

        }
    }

     fun levelProgress(){
        val newLevel = prefs.getLevel() + 1
        prefs.saveLevel(newLevel)

    }

    fun setDialogFlag(flag:Boolean){
        prefs.setDialogFlag(flag)
    }

    fun upDialogFlag():Boolean{
        return prefs.updialogFlag()
    }


}