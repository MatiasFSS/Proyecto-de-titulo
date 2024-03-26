package com.copetiny.proyecto.ui.detailtutorials

import com.copetiny.proyecto.domain.model.tutorials.TutorialsModel

sealed class TutorialsDetailState {
    data object Loading:TutorialsDetailState()
    data class Error(val error:String):TutorialsDetailState()
    data class Success( val nombre_tutorial:String, val descripcion_tutorial:String, val informacion_tutorial:String, val tutorialsModel: TutorialsModel):TutorialsDetailState()
}