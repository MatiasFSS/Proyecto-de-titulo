package com.copetiny.proyecto.data.network.response.quiz

import com.copetiny.proyecto.domain.model.quiz.QuestionModel
import com.google.gson.annotations.SerializedName


class QuizResponse(
    @SerializedName("pregunta") val pregunta:String,
    @SerializedName("alternativaA") val alternativaA:String,
    @SerializedName("alternativaB") val alternativaB:String,
    @SerializedName("alternativaC") val alternativaC:String,
    @SerializedName("alternativaD") val alternativaD:String,
    @SerializedName("respuesta")  val respuesta:Int,
    @SerializedName("dificultad") val dificultad:String
){
    fun toDomainQuiz(): List<QuestionModel>? {
        return listOf( QuestionModel(
                pregunta = pregunta,
                alternativaA = alternativaA,
                alternativaB = alternativaB,
                alternativaC = alternativaC,
                alternativaD = alternativaD,
                respuesta = respuesta,
                dificultad = dificultad
            ))

    }
}