package com.copetiny.proyecto.ui.detailencyclopedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaModel
import com.copetiny.proyecto.domain.usecase.GetEncyclopediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EncyclopediaDetailViewModel @Inject constructor(private val getEncyclopediaUseCase: GetEncyclopediaUseCase):ViewModel() {

    private var _state = MutableStateFlow<EncyclopediaDetailState>(EncyclopediaDetailState.Loading)
    val state : StateFlow<EncyclopediaDetailState> = _state

    lateinit var encyclopedia: EncyclopediaModel

    fun getEncyclopedia(id: EncyclopediaModel){
        encyclopedia = id
        viewModelScope.launch {
            _state.value = EncyclopediaDetailState.Loading
           val result = withContext(Dispatchers.IO){
                   getEncyclopediaUseCase(id.name)


           }

            if(result!= null){
                _state.value = EncyclopediaDetailState.Success( result.nombre_material,  result.descripcion,result.informacion, encyclopedia)
            }else{
                _state.value = EncyclopediaDetailState.Error("Ha ocurrido un error, intente más tarde")
            }

        }

    }
}