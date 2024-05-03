package com.copetiny.proyecto.ui.detailquiz
import com.copetiny.proyecto.domain.model.quiz.QuizModel

sealed class QuizDetailState {
    data object Loading: QuizDetailState()
    data class Error(val error:String): QuizDetailState()
    data class Success(val pregunta:String, val alternativaA:String, val alternativaB:String, val alternativaC:String, val alternativaD:String, val respuesta:Int, val dificultad:String, val questionModel:QuizModel): QuizDetailState()
}