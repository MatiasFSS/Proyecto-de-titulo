package com.copetiny.proyecto.ui.questionday

sealed class QuestionDayState {
    data object Loading:QuestionDayState()
    data class Error(val error:String):QuestionDayState()
    data class Success(val pregunta: String, val alternativaA:String, val alternativaB:String, val alternativaC:String, val alternativaD:String, val respuesta:String):QuestionDayState()
}