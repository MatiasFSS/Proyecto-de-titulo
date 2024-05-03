package com.copetiny.proyecto.domain.model.quiz

data class QuestionModel(
    val pregunta: String,
    val alternativaA:String,
    val alternativaB:String,
    val alternativaC:String,
    val alternativaD:String,
    val respuesta: Int,
    val dificultad:String
)