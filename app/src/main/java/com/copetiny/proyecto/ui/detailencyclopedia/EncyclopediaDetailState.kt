package com.copetiny.proyecto.ui.detailencyclopedia

import com.copetiny.proyecto.domain.model.EncyclopediaModel

sealed class EncyclopediaDetailState {
    data object Loading:EncyclopediaDetailState()
    data class Error(val error:String):EncyclopediaDetailState()
    data class Success( val nombre_material:String, val descripcion:String, val informacion:String, val encyclopediaModel: EncyclopediaModel):EncyclopediaDetailState()
}