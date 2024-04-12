package com.copetiny.proyecto.ui.detailtutorials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copetiny.proyecto.domain.model.tutorials.TutorialsModel
import com.copetiny.proyecto.domain.usecase.GetTutorialsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TutorialsDetailViewModel @Inject constructor(private val getTutorialsUseCase: GetTutorialsUseCase):ViewModel() {
    private var _state = MutableStateFlow<TutorialsDetailState>(TutorialsDetailState.Loading)
    val state:StateFlow<TutorialsDetailState> = _state

    lateinit var tutorials:TutorialsModel

    fun getTutorials(id:TutorialsModel){
        tutorials = id
        viewModelScope.launch {
            _state.value = TutorialsDetailState.Loading
            val result = withContext(Dispatchers.IO){
                getTutorialsUseCase(id.name)
            }

            if(result!= null){
                _state.value = TutorialsDetailState.Success(/*result.nombre_tutorial,*/ result.descripcion_tutorial, result.informacion_tutorial, tutorials)
            }else{
                _state.value = TutorialsDetailState.Error("Ha ocurrido un error, intentelo más tarde")
            }
        }
    }

}